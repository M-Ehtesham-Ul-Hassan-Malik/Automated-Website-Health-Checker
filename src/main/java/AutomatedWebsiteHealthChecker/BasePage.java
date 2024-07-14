package AutomatedWebsiteHealthChecker;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;


    public BasePage()  {
        System.setProperty("webdriver.chrome.driver", "E:\\My Learning\\JAVA\\AutomatedWebsiteHealthChecker\\src\\main\\java\\Driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, 10);
    }



    public void openUrl(String url) {
        driver.get(url);
    }


    public void closeBrowser() {
        driver.quit();
    }
}