package AutomatedWebsiteHealthChecker;



import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class main extends BasePage {
    Properties prop = Utils.ConfigUtils.getProps("data");



    @Test
    public void navigateToWebsite() {
        driver.get(prop.getProperty("url"));
        System.out.println("Page title is: " + driver.getTitle());
    }

    @Test
    public void checkPageLoadTime() {
        long startTime = System.currentTimeMillis();
        driver.get("https://nust.edu.pk/");
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("Page load time: " + loadTime + " milliseconds");
        Assert.assertTrue(loadTime < 5000, "Page load time is too long!");
    }

    @Test
    public void checkBrokenLinks() {
        driver.get("https://nust.edu.pk/");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty() && !url.equals("https://nust.edu.pk/") && (url.startsWith("http") || url.startsWith("https"))) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Checking URL: " + url + " - Response Code: " + responseCode);
                    if (responseCode == 403) {
                        System.out.println("Skipping URL: " + url + " due to 403 Forbidden status");
                        continue;
                    }
                    Assert.assertTrue(responseCode < 400, "Broken link: " + url);
                } catch (IOException e) {
                    System.err.println("Exception checking link: " + url + " - " + e.getMessage());
                    Assert.fail("Exception checking link: " + url, e);
                }
            }
        }
    }


    @Test
    public void checkMissingImages() {
        driver.get("https://nust.edu.pk/");
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement image : images) {
            String src = image.getAttribute("src");
            if (src != null && !src.isEmpty() && (src.startsWith("http") || src.startsWith("https"))) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(src).openConnection();
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Checking Image: " + src + " - Response Code: " + responseCode);
                    Assert.assertTrue(responseCode < 400, "Broken image: " + src);
                } catch (MalformedURLException e) {
                    System.err.println("Malformed URL: " + src + " - " + e.getMessage());
                    Assert.fail("Malformed URL: " + src, e);
                } catch (IOException e) {
                    System.err.println("Exception checking image: " + src + " - " + e.getMessage());
                    Assert.fail("Exception checking image: " + src, e);
                }
            }
        }
    }
}
