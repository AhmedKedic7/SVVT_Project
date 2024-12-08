import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.beans.IntrospectionException;
import java.util.List;

public class Main {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver"); // ovo promjeniti ako ti je na drugacijoj lokaciji (ako budes mijenjao nemoj brisati moje vec samo stavi pod komentar :D)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        if(webDriver != null) webDriver.quit();
    }

    @Test
    public void login() throws InterruptedException {
        webDriver.get("https://olx.ba/");

        WebElement slazemSeBtn = webDriver.findElement(By.xpath("//button[@mode='primary']")); // Mora se prvo kliknuti 'slazem se' button inace mogu nsatati problemi :)
        slazemSeBtn.click();

        WebElement loginBtn = webDriver.findElement(By.xpath("//a[@href='/login']"));
        loginBtn.click();


        webDriver.get("https://olx.ba/login");

        WebElement username = webDriver.findElement(By.xpath("//input[@name='username']"));
        username.sendKeys("Dino Muslic");
        WebElement password = webDriver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("Neka Sifra");

        loginBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        loginBtn.click();

        Thread.sleep(5000);
    }

    @Test
    public void registerKlasicniProfil() throws InterruptedException {
        webDriver.get("https://olx.ba/");

        WebElement slazemSeBtn = webDriver.findElement(By.xpath("//button[@mode='primary']")); // Mora se prvo kliknuti 'slazem se' button inace mogu nsatati problemi :)
        slazemSeBtn.click();

        WebElement registerBtn = webDriver.findElement(By.xpath("//a[@href='/register']"));
        registerBtn.click();


        webDriver.get("https://olx.ba/register");

        List<WebElement> inputFields = webDriver.findElements(By.tagName("input")); // spol, regija i mjesto nisu uracunati jer su select, a ne input
        WebElement email_brojTel = inputFields.get(0);
        WebElement sifra = inputFields.get(1);
        WebElement vaseOlxIme = inputFields.get(2);
        WebElement slazemSeSaUslovima = inputFields.get(3);

        Select spol = new Select(webDriver.findElement(By.xpath("//select[@data-v-4549ed56='']")));
        spol.selectByValue("male");

        email_brojTel.sendKeys("neki_email@gmail.com");
        sifra.sendKeys("neka sifra");
        vaseOlxIme.sendKeys("MojeOlxIme");
        slazemSeSaUslovima.click();

        Select regija = new Select(webDriver.findElement(By.xpath("//select[@label='Regija']")));
        regija.selectByValue("1");

        Thread.sleep(1000); // mora ici posto kad klikne regiju treba da klikne mjesto al prebrzo to odradi bez sleep-a pa mora malo sleepati (iskoci error kako nema mjesto Bihac npr)

        Select mjesto = new Select(webDriver.findElement((By.xpath("//select[@label='Mjesto']"))));
        mjesto.selectByVisibleText("Bihać");

        registerBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        registerBtn.click();

        Thread.sleep(7000);
    }

    @Test
    public void registerOLXShop() throws InterruptedException {
        webDriver.get("https://olx.ba/");

        WebElement slazemSeBtn = webDriver.findElement(By.xpath("//button[@mode='primary']")); // Mora se prvo kliknuti 'slazem se' button inace mogu nsatati problemi :)
        slazemSeBtn.click();

        WebElement registerBtn = webDriver.findElement(By.xpath("//a[@href='/register']"));
        registerBtn.click();

        WebElement olxShopBtn = webDriver.findElement(By.xpath("//li[@data-v-4549ed56=''][2]"));
        olxShopBtn.click();

        List<WebElement> inputFields = webDriver.findElements(By.xpath("//input[@data-v-1c6f47e2='']")); // vraca sve input fieldove gdje se manuelno unosi nesto tj, nema selectanja (email, sifra, ime firme, id broj, telefon, web stranica)
        WebElement email = inputFields.get(0);
        email.sendKeys("neki_email@gmail.com");
        WebElement sifra = inputFields.get(1);
        sifra.sendKeys("neka sifra");
        WebElement vaseOlxIme = inputFields.get(2);
        vaseOlxIme.sendKeys("MojeOLXIme");

        Select paket = new Select(webDriver.findElement(By.xpath("//select[@data-v-5da4175e='']")));
        paket.selectByValue("4");

        Select kategorijaOlxShopa = new Select(webDriver.findElement(By.xpath("//select[@data-v-03501609=\"\"]")));
        kategorijaOlxShopa.selectByVisibleText("Kompjuteri");

        WebElement idBroj = inputFields.get(3);
        idBroj.sendKeys("101");

        WebElement telefon = inputFields.get(4);
        telefon.sendKeys("061 123 456");

        WebElement webStranica = inputFields.get(5);
        webStranica.sendKeys("https://nekastranica.com");

        Select regija = new Select(webDriver.findElement(By.xpath("//select[@label='Regija']")));
        regija.selectByValue("1");

        Thread.sleep(1000); // mora ici posto kad klikne regiju treba da klikne mjesto al prebrzo to odradi bez sleep-a pa mora malo sleepati (iskoci error kako nema mjesto Bihac npr)

        Select mjesto = new Select(webDriver.findElement((By.xpath("//select[@label='Mjesto']"))));
        Thread.sleep(1000);
        mjesto.selectByVisibleText("Bihać");

        WebElement slazemSeSaUslovima = webDriver.findElement(By.xpath("//input[@id='checkbox']"));
        slazemSeSaUslovima.click();

        registerBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        registerBtn.click();

        Thread.sleep(5000);
    }

    @Test
    public void pretraga() throws InterruptedException {
        webDriver.get("https://olx.ba/");

        WebElement slazemSeBtn = webDriver.findElement(By.xpath("//button[@mode='primary']")); // Mora se prvo kliknuti 'slazem se' button inace mogu nsatati problemi :)
        slazemSeBtn.click();

        WebElement pretraga = webDriver.findElement(By.xpath("//input[@placeholder='Pretraga']"));
        pretraga.sendKeys("mobitel");
        pretraga.sendKeys(Keys.ENTER);

        Thread.sleep(10000);
    }
}
