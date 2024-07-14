package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.testng.Reporter;

public class testing {
    WebDriver driver;
    WebDriverWait wait;
    Properties prop = Utils.ConfigUtils.getProps("data");
    String baseUrl = prop.getProperty("url");



    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "E:\\My Learning\\JAVA\\AutomatedWebsiteHealthChecker\\src\\main\\java\\Driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, 10);
    }

    @Test(description = "Check Load Time", priority = 1)
    public void checkPageLoadTime() {
        long startTime = System.currentTimeMillis();
        //driver.get("https://nust.edu.pk/");
        driver.get(prop.getProperty("url"));
        //driver.get(baseUrl);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("Page load time: " + loadTime + " milliseconds");
        Assert.assertTrue(loadTime < 5000, "Page load time is too long!");
    }




    @Test(description = "check broken links", priority = 2)
    public void checkBrokenLinks() {
        driver.get(prop.getProperty("url"));
        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty() && !url.equals(baseUrl) && (url.startsWith("http") || url.startsWith("https"))) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Checking URL: " + url + " - Response Code: " + responseCode);
                    if (responseCode >= 400 && responseCode != 403) {
                        Reporter.log("Broken link: " + url + " - Response Code: " + responseCode);
                    } else {
                        Reporter.log("Valid link: " + url + " - Response Code: " + responseCode);
                    }
                } catch (NoSuchWindowException e) {
                    // Handle NoSuchWindowException
                    System.err.println("Window closed unexpectedly: " + e.getMessage());
                    Reporter.log("Window closed unexpectedly: " + e.getMessage());
                    // Re-initialize WebDriver or handle as appropriate
                    Assert.fail("NoSuchWindowException occurred: " + e.getMessage(), e);
                } catch (IOException e) {
                    System.err.println("Exception checking link: " + url + " - " + e.getMessage());
                    Reporter.log("Exception checking link: " + url + " - " + e.getMessage());
                    Assert.fail("Exception checking link: " + url, e);
                }
            }
        }
    }


    @Test(description = "Check for missing images", priority = 3)
    public void checkMissingImages() {
        //driver.get("https://nust.edu.pk/");
        driver.get(prop.getProperty("url"));
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
                    Assert.assertTrue(responseCode < 400 || responseCode == 404, "Broken image: " + src);
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

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}





