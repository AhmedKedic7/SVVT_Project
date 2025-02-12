import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginRegistration {
    private static WebDriver webDriver;
    private static String baseUrl = "https://olx.ba/";
    private static WebDriverWait webDriverWait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/ahmed/chromedriver-linux64/chromedriver"); // ovo promjeniti ako ti je na drugacijoj lokaciji (ako budes mijenjao nemoj brisati moje vec samo stavi pod komentar :D)
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
    public void login() throws InterruptedException {
        WebElement loginBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/login']")));
        loginBtn.click();

        WebElement username = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        username.sendKeys("SVVTProjekat558");
        WebElement password = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        password.sendKeys("najjacasifraikada123");

        loginBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        loginBtn.click();

        Thread.sleep(6000);
        assertEquals(baseUrl, webDriver.getCurrentUrl());
        WebElement profileLink=webDriver.findElement(By.xpath("//*[@id=\"__layout\"]/div/header/div/div[1]/div[1]/div[2]/div[2]"));
        assertTrue(profileLink.isDisplayed());
    }

    @Test
    public void loginFail() throws InterruptedException {
        WebElement loginBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/login']")));
        loginBtn.click();

        WebElement username = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        username.sendKeys("SVVTProjekat558");
        WebElement password = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        password.sendKeys("najjacasifraikada1234");

        loginBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        loginBtn.click();

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Podaci nisu tačni.", toastr.getText());

        Thread.sleep(5000);

    }

    @Test
    public void logout() throws InterruptedException {
        login();

        WebElement menuBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/svg/menu.svg']")));
        menuBtn.click();

        Thread.sleep(2000);

        WebElement logoutBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='left logout']")));
        Thread.sleep(1000);
        logoutBtn.click();

        Thread.sleep(5000);
        WebElement loginAndRegisterBtn=webDriver.findElement(By.xpath("//*[@id=\"__layout\"]/div/header/div/div[1]/div[1]/div[2]"));
        assertTrue(loginAndRegisterBtn.isDisplayed());
    }

    @Test
    public void registerKlasicniProfil() throws InterruptedException {
        //getTempMail();
        Thread.sleep(3000);
        WebElement registerBtn =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/register']")));
        registerBtn.click();

        List<WebElement> inputFields = webDriver.findElements(By.tagName("input"));
        WebElement email_brojTel = inputFields.get(0);
        WebElement sifra = inputFields.get(1);
        WebElement vaseOlxIme = inputFields.get(2);
        WebElement slazemSeSaUslovima = inputFields.get(3);

        Select spol = new Select(webDriver.findElement(By.xpath("//select[@data-v-5da4175e='']")));
        spol.selectByValue("male");

        email_brojTel.sendKeys("neki mail");
        sifra.sendKeys("jakojakasifra312");
        vaseOlxIme.sendKeys("hd_asj_dha_h");

        slazemSeSaUslovima.click();

        Select regija = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Regija']"))));
        regija.selectByValue("1");

        Select mjesto = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Mjesto']"))));
        Thread.sleep(1000);
        mjesto.selectByVisibleText("Bihać");

        registerBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-v-3de08799='']")));
        registerBtn.click();

        Thread.sleep(5000);

        assertEquals("https://olx.ba/vodic", webDriver.getCurrentUrl());
        Thread.sleep(1000);
    }

    @Test
    public void registerKlasicniProfilEmailFail() throws InterruptedException {
        Thread.sleep(3000);
        WebElement registerBtn =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/register']")));
        registerBtn.click();

        List<WebElement> inputFields = webDriver.findElements(By.tagName("input")); // spol, regija i mjesto nisu uracunati jer su select, a ne input
        WebElement email_brojTel = inputFields.get(0);
        WebElement sifra = inputFields.get(1);
        WebElement vaseOlxIme = inputFields.get(2);
        WebElement slazemSeSaUslovima = inputFields.get(3);

        Select spol = new Select(webDriver.findElement(By.xpath("//select[@data-v-5da4175e='']")));
        spol.selectByValue("male");

        email_brojTel.sendKeys("neki mail");
        sifra.sendKeys("jakojakasifra312");
        vaseOlxIme.sendKeys("hd_asj_dha_h");

        slazemSeSaUslovima.click();

        Select regija = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Regija']"))));
        regija.selectByValue("1");

        Select mjesto = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Mjesto']"))));
        Thread.sleep(1000);
        mjesto.selectByVisibleText("Bihać");

        registerBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-v-3de08799='']")));
        registerBtn.click();

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Polje email mora biti validna e-mail addressa.", toastr.getText());

        Thread.sleep(5000);
    }

    @Test
    public void registerKlasicniProfilPasswordFail() throws InterruptedException {
        Thread.sleep(3000);
        WebElement registerBtn =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/register']")));
        registerBtn.click();

        List<WebElement> inputFields = webDriver.findElements(By.tagName("input")); // spol, regija i mjesto nisu uracunati jer su select, a ne input
        WebElement email_brojTel = inputFields.get(0);
        WebElement sifra = inputFields.get(1);
        WebElement vaseOlxIme = inputFields.get(2);
        WebElement slazemSeSaUslovima = inputFields.get(3);

        Select spol = new Select(webDriver.findElement(By.xpath("//select[@data-v-5da4175e='']")));
        spol.selectByValue("male");

        email_brojTel.sendKeys("bosnabanana123@gmail.com");
        sifra.sendKeys("@@");
        vaseOlxIme.sendKeys("hd__ooo_aaa");

        slazemSeSaUslovima.click();

        Select regija = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Regija']"))));
        regija.selectByValue("1");

        Select mjesto = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Mjesto']"))));
        Thread.sleep(1000);
        mjesto.selectByVisibleText("Bihać");

        registerBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-v-3de08799='']")));
        registerBtn.click();

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Šifra ne smije biti kraća od 8 karaktera", toastr.getText());

        Thread.sleep(5000);
    }

    @Test
    public void registerOLXShop() throws InterruptedException {
        WebElement registerBtn = webDriver.findElement(By.xpath("//a[@href='/register']"));
        registerBtn.click();

        WebElement olxShopBtn = webDriver.findElement(By.xpath("//ul[@class='register-types']//li[2]"));
        olxShopBtn.click();

        List<WebElement> inputFields = webDriver.findElements(By.xpath("//input[@data-v-1c6f47e2='']"));

        WebElement email = inputFields.get(0);
        email.sendKeys("neki_email@gmail.com");
        WebElement sifra = inputFields.get(1);
        sifra.sendKeys("neka sifra");
        WebElement vaseOlxIme = inputFields.get(2);
        vaseOlxIme.sendKeys("MojeOLXIme");

        Select paket = new Select(webDriver.findElement(By.xpath("//select[@data-v-5da4175e='']")));
        paket.selectByValue("4");

        Select kategorijaOlxShopa = new Select(webDriver.findElement(By.xpath("//select[@data-v-03501609=\"\"]")));
        Thread.sleep(1000);
        kategorijaOlxShopa.selectByVisibleText("Kompjuteri");

        WebElement idBroj = inputFields.get(3);
        idBroj.sendKeys("101");

        WebElement telefon = inputFields.get(4);
        telefon.sendKeys("061 123 456");

        WebElement webStranica = inputFields.get(5);
        webStranica.sendKeys("https://nekastranica.com");

        Select regija = new Select(webDriver.findElement(By.xpath("//select[@label='Regija']")));
        regija.selectByValue("1");

        Thread.sleep(1000);

        Select mjesto = new Select(webDriver.findElement((By.xpath("//select[@label='Mjesto']"))));
        Thread.sleep(1000);
        mjesto.selectByVisibleText("Bihać");

        WebElement slazemSeSaUslovima = webDriver.findElement(By.xpath("//input[@id='checkbox']"));
        slazemSeSaUslovima.click();

        registerBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        registerBtn.click();

        Thread.sleep(5000);

        assertEquals("https://olx.ba/vodic", webDriver.getCurrentUrl());
        Thread.sleep(1000);
    }
}
