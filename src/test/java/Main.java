import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {
    private static WebDriver webDriver;
    private static String baseUrl = "https://olx.ba/";
    private static WebDriverWait webDriverWait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver"); // ovo promjeniti ako ti je na drugacijoj lokaciji (ako budes mijenjao nemoj brisati moje vec samo stavi pod komentar :D)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        webDriver = new ChromeDriver(options);
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get(baseUrl);
        webDriver.manage().window().maximize();

        WebElement slazemSeBtn =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@mode='primary']")));
        slazemSeBtn.click();
    }

    @AfterEach
    public void tearDown() {
        if(webDriver != null) webDriver.quit();
    }

    @Test
    public void https() {
        webDriver.get(baseUrl);
        assertTrue(webDriver.getCurrentUrl().startsWith("https"));
    }

    String tempMail;
    @Test
    public void getTempMail() throws InterruptedException {
        webDriver.get("https://temp-mail.org/en/");

        WebElement mail = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='mail']")));
        Thread.sleep(1000);
        tempMail = mail.getAttribute("value");
        System.out.println(tempMail);
        webDriver.get("https://olx.ba/register");
    }
}