import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver"); // ovo promjeniti ako ti je na drugacijoj lokaciji (ako budes mijenjao nemoj brisati moje vec samo stavi pod komentar :D)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);

        webDriver.get("https://olx.ba/");
        webDriver.manage().window().maximize();
        WebElement slazemSeBtn = webDriver.findElement(By.xpath("//button[@mode='primary']"));
        slazemSeBtn.click();
    }

    @AfterEach
    public void tearDown() {
        if(webDriver != null) webDriver.quit();
    }

    @Test
    public void login() throws InterruptedException {
        WebElement loginBtn = webDriver.findElement(By.xpath("//a[@href='/login']"));
        loginBtn.click();

        WebElement username = webDriver.findElement(By.xpath("//input[@name='username']"));
        username.sendKeys("SVVTProjekat558");
        WebElement password = webDriver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("najjacasifraikada123");

        loginBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        loginBtn.click();

        Thread.sleep(5000);
    }

    String tempMail;
    @Test
    public void getTempMail() throws InterruptedException {
        webDriver.get("https://temp-mail.org/en/");
        Thread.sleep(5000);

        WebElement mail = webDriver.findElement(By.xpath("//input[@id='mail']"));
        Thread.sleep(1000);
        tempMail = mail.getAttribute("value");
        System.out.println(tempMail);
        webDriver.get("https://olx.ba/register");
    }

    @Test
    public void registerKlasicniProfil() throws InterruptedException {
        //getTempMail();

        Thread.sleep(3000);

        /*WebElement registerBtn = webDriver.findElement(By.xpath("//a[@href='/register']"));
        registerBtn.click();*/

        List<WebElement> inputFields = webDriver.findElements(By.tagName("input")); // spol, regija i mjesto nisu uracunati jer su select, a ne input
        WebElement email_brojTel = inputFields.get(0);
        WebElement sifra = inputFields.get(1);
        WebElement vaseOlxIme = inputFields.get(2);
        WebElement slazemSeSaUslovima = inputFields.get(3);

        Select spol = new Select(webDriver.findElement(By.xpath("//select[@data-v-5da4175e='']")));
        spol.selectByValue("male");

        email_brojTel.sendKeys(tempMail);
        sifra.sendKeys("jakojakasifra312");
        vaseOlxIme.sendKeys("hd_asj_dha_h");

        /*email_brojTel.sendKeys("svvt3567@gmail.com");
        sifra.sendKeys("najjacasifraikada123");
        vaseOlxIme.sendKeys("SVVTProjekat558");*/
        slazemSeSaUslovima.click();

        Select regija = new Select(webDriver.findElement(By.xpath("//select[@label='Regija']")));
        regija.selectByValue("1");

        Thread.sleep(1000); // mora ici posto kad klikne regiju treba da klikne mjesto al prebrzo to odradi bez sleep-a pa mora malo sleepati (iskoci error kako nema mjesto Bihac npr)

        Select mjesto = new Select(webDriver.findElement((By.xpath("//select[@label='Mjesto']"))));
        mjesto.selectByVisibleText("Bihać");

        WebElement registerBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        registerBtn.click();

        Thread.sleep(7000);
    }

    @Test
    public void registerOLXShop() throws InterruptedException {
        WebElement registerBtn = webDriver.findElement(By.xpath("//a[@href='/register']"));
        registerBtn.click();

        WebElement olxShopBtn = webDriver.findElement(By.xpath("//ul[@class='register-types']//li[2]"));
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

        Thread.sleep(1000);

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
        WebElement pretraga = webDriver.findElement(By.xpath("//input[@placeholder='Pretraga']"));
        pretraga.sendKeys("mobitel");
        pretraga.sendKeys(Keys.ENTER);

        Thread.sleep(3000);

        WebElement mobitel = webDriver.findElement(By.xpath("//img[@data-v-20c6ee96='']"));
        mobitel.click();

        Thread.sleep(1000);

        WebElement detalji = webDriver.findElement(By.xpath("//div[@class='central-inner sm:pb-lg hide']"));
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

        Thread.sleep(5000);
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
            System.out.println(i + ". iteration -> " + key + ": " + value);

            if(
                    !key.equals("https://pomoc.olx.ba/hc/bs") &&
                    !key.equals("/o-olxu/o-nama") &&
                    !key.equals("/o-olxu/uslovi-koristenja") &&
                    !key.equals("/o-olxu/olxkredit") &&
                    !key.equals("/o-olxu/online-sigurnost") &&
                    !key.equals("/o-olxu/privatnost-podataka")
            ) {
                WebElement webElement = webDriver.findElement(By.xpath("//a[@href='" + key + "']"));
                Thread.sleep(1000);
                webElement.click();
                Thread.sleep(1000);
                Thread.sleep(1000);
            } else {
                List<WebElement> webElements = webDriver.findElements(By.xpath("//button[@data-v-473a361f='']")); // ima vise ovih //button[@data-v-473a361f=''] nama treba 2. po redu tj na 1. indexu liste.
                WebElement ostaliLinkoviBtn = webElements.get(1);
                ostaliLinkoviBtn.click();
                WebElement webElement = webDriver.findElement(By.xpath("//a[@href='" + key + "']"));
                Thread.sleep(1000);
                webElement.click();
                Thread.sleep(1000);
            }
            i++;
        }
    }

    @Test
    public void otidjiNaKategoriju() throws InterruptedException {
        WebElement kategorijeLink = webDriver.findElement(By.xpath("//a[@href='/kategorije']"));
        kategorijeLink.click();

        Thread.sleep(3000);
        WebElement kategorija = webDriver.findElement(By.xpath("//a[@href='/pretraga?category_id=884']")); // prikolice
        Thread.sleep(1000);
        kategorija.click();

        Thread.sleep(5000);
        WebElement prikolica = webDriver.findElement(By.xpath("//div[@class='min-h-13 h-170 relative hover-image']"));
        prikolica.click();

        Thread.sleep(3000);
    }

    @Test
    public void dodajUKorpu() throws InterruptedException {
        webDriver.get("https://parfemizavas.olx.ba/aktivni");
        Thread.sleep(1000);

        WebElement parfem = webDriver.findElement(By.xpath("//img[@class='listing-image-main loaded h-full object-cover h-13']"));
        parfem.click();

        Thread.sleep(3000);
        login();

        List<WebElement> sviButtoni = webDriver.findElements(By.xpath("//button[@data-v-3de08799='']")); // dodaj u korpu je 10.
        WebElement dodajUKorpu = sviButtoni.get(9);
        Thread.sleep(1000);
        dodajUKorpu.click();

        Thread.sleep(5000);
    }

    @Test
    public void listajStory() throws InterruptedException {
        WebElement idiDesnoButton = webDriver.findElement(By.xpath("//button[@data-glide-dir='>']"));

        for(int i = 0; i < 10; i++) {
            idiDesnoButton.click();
            Thread.sleep(1000);
        }
    }

    @Test
    public void filtrirajIzdvojeneOglase() throws InterruptedException {
        Thread.sleep(1000);
        WebElement filtrirajBtn = webDriver.findElement(By.xpath("//div[@class='px-md mb-lg flex items-center pt-lg justify-between']//button"));
        filtrirajBtn.click();

        Thread.sleep(1000);
        WebElement kompjuteri = webDriver.findElement(By.xpath("//ul[@class='custom-list checkbox']//li[6]"));
        kompjuteri.click();

        Thread.sleep(3000);
    }

    // otici na profil osobe koja je objavila oglas i napisati im nesto

    // podijeliti link oglasa

    // dodavanje i brisanje komentara

    // dodati filtere god onih okruglih ...

    // dodati filere za oglase

    // popraviti registar

    // mijenjanje korisnicko info

    // izbaciti iz korpe
    @Test
    public void izbaciIzKorpe() throws InterruptedException {
        login();
        webDriver.get("https://olx.ba/");
        Thread.sleep(1000);
        WebElement shoppingCart = webDriver.findElement(By.xpath("//button[@class='flex items-center mr-md my-articles border-r border-gray-400 pr-md']"));
        shoppingCart.click();
        Thread.sleep(1000);
        WebElement xButton = webDriver.findElement(By.xpath("//button[@class='h-7 w-7']"));
        xButton.click();
        Thread.sleep(3000);
    }

    // objavi oglas

    // edit oglas
}
