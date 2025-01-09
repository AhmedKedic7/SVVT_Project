import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Search {
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
    public void pretraga() throws InterruptedException {
        WebElement pretraga = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Pretraga']")));
        pretraga.sendKeys("mobitel");
        pretraga.sendKeys(Keys.ENTER);

        WebElement mobitel = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@data-v-20c6ee96='']")));
        mobitel.click();

        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,800)");
        Thread.sleep(7000);

        WebElement detalji = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='central-inner sm:pb-lg hide']")));
        assertTrue(
                detalji.getText().contains("mobitel")
                        || detalji.getText().contains("telefon")
                        || detalji.getText().contains("smartphone")
                        || detalji.getText().contains("Mobitel")
                        || detalji.getText().contains("Telefon")
                        || detalji.getText().contains("Smartphohe")
                        || detalji.getText().contains("MOBITEL")
                        || detalji.getText().contains("TELEFON")
                        || detalji.getText().contains("SMARTPHONE")
        );

        Thread.sleep(3000);
    }

    @Test
    public void pretragaFail() throws InterruptedException {
        WebElement pretraga = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Pretraga']")));
        pretraga.sendKeys("djfskfjsdklfjs");
        pretraga.sendKeys(Keys.ENTER);

        WebElement poruka = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='w-full px-md']/h1")));
        assertEquals("Nema rezultata za traženi pojam", poruka.getText());

        Thread.sleep(3000);
    }

    @Test
    public void homeLinkovi() throws InterruptedException {

        LinkedHashMap<String, String> hrefs_stranice = new LinkedHashMap<>();
        hrefs_stranice.put("/", "https://olx.ba/");
        hrefs_stranice.put("/kategorije", "https://olx.ba/kategorije");
        hrefs_stranice.put("/shopovi", "https://olx.ba/shopovi");
        hrefs_stranice.put("https://marketing.olx.ba/", "https://marketing.olx.ba/");
        hrefs_stranice.put("https://blog.olx.ba/", "https://blog.olx.ba/");
        hrefs_stranice.put("https://pomoc.olx.ba/hc/bs", "https://pomoc.olx.ba/hc/bs");
        hrefs_stranice.put("/o-olxu/o-nama", "https://olx.ba/o-olxu/o-nama");
        hrefs_stranice.put("/o-olxu/uslovi-koristenja", "https://olx.ba/o-olxu/uslovi-koristenja");
        hrefs_stranice.put("/o-olxu/olxkredit", "https://olx.ba/o-olxu/olxkredit");
        hrefs_stranice.put("/o-olxu/online-sigurnost", "https://olx.ba/o-olxu/online-sigurnost");
        hrefs_stranice.put("/o-olxu/privatnost-podataka", "https://olx.ba/o-olxu/privatnost-podataka");


        int i = 1;
        for (Map.Entry<String, String> entry : hrefs_stranice.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(i + ". iteration -> " + key + ": " + value); // ove 3 linije koda su bile samo da debugganje ali nek stoje za svaki slucaj ako opet bute kakvih problema

            if(
                    !key.equals("https://pomoc.olx.ba/hc/bs") &&
                            !key.equals("/o-olxu/o-nama") &&
                            !key.equals("/o-olxu/uslovi-koristenja") &&
                            !key.equals("/o-olxu/olxkredit") &&
                            !key.equals("/o-olxu/online-sigurnost") &&
                            !key.equals("/o-olxu/privatnost-podataka")
            ) {
                WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='" + key + "']")));
                Thread.sleep(1000);
                webElement.click();
            } else {
                List<WebElement> webElements = webDriver.findElements(By.xpath("//button[@data-v-473a361f='']")); // ima vise ovih //button[@data-v-473a361f=''] nama treba 2. po redu tj na 1. indexu liste.
                WebElement ostaliLinkoviBtn = webElements.get(1);
                ostaliLinkoviBtn.click();
                WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='" + key + "']")));
                Thread.sleep(1000);
                webElement.click();
            }
            i++;
        }
    }

    @Test
    public void otidjiNaKategoriju() throws InterruptedException {
        WebElement kategorijeLink = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/kategorije']")));
        kategorijeLink.click();

        WebElement kategorija = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/pretraga?category_id=884']"))); // prikolice
        kategorija.click();

        WebElement prikolica = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='min-h-13 h-170 relative hover-image']")));
        prikolica.click();

        Thread.sleep(5000);
    }

    /*Ovi storiji izgleda ne postoje vise  :(
    @Test
    public void listajStory() throws InterruptedException {
        WebElement idiDesnoButton = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-glide-dir='>']")));

        for(int i = 0; i < 10; i++) {
            idiDesnoButton.click();
            Thread.sleep(1000);
        }
    }*/
}
