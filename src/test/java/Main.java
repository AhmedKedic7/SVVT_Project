import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
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
    public void login() throws InterruptedException {
        WebElement loginBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/login']")));
        loginBtn.click();

        WebElement username = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        username.sendKeys("SVVTProjekat558");
        WebElement password = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        password.sendKeys("najjacasifraikada123");

        loginBtn = webDriver.findElement(By.xpath("//button[@data-v-3de08799='']"));
        loginBtn.click();

        Thread.sleep(3000);
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

    @Test
    public void registerKlasicniProfil() throws InterruptedException {
        //getTempMail();

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

        /*email_brojTel.sendKeys("svvt3567@gmail.com");
        sifra.sendKeys("najjacasifraikada123");
        vaseOlxIme.sendKeys("SVVTProjekat558");*/
        slazemSeSaUslovima.click();

        Select regija = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Regija']"))));
        regija.selectByValue("1");

        Select mjesto = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Mjesto']"))));
        Thread.sleep(1000);
        mjesto.selectByVisibleText("Bihać");

        registerBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-v-3de08799='']")));
        registerBtn.click();

        Thread.sleep(5000);
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

        WebElement item = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@data-v-20c6ee96='']")));
        item.click();

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

    @Test
    public void dodajUKorpu() throws InterruptedException {
        webDriver.get("https://parfemizavas.olx.ba/aktivni");

        WebElement parfem = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='listing-image-main loaded h-full object-cover h-13']")));
        parfem.click();

        Thread.sleep(3000);
        login();

        List<WebElement> dodajUKorpu = webDriver.findElements(By.xpath("//button[.//text()[contains(., 'Dodaj u korpu')]]"));
        Thread.sleep(1000);
        dodajUKorpu.get(1).click();

        Thread.sleep(3000);
    }

    @Test
    public void izbaciIzKorpe() throws InterruptedException {
        login();
        //dodajUKorpu();
        webDriver.get(baseUrl);
        WebElement shoppingCart = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='flex items-center mr-md my-articles border-r border-gray-400 pr-md']")));
        shoppingCart.click();
        WebElement xButton =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='h-7 w-7']")));
        Thread.sleep(2000);
        xButton.click();
        Thread.sleep(3000);
    }

    @Test
    public void listajStory() throws InterruptedException {
        WebElement idiDesnoButton = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-glide-dir='>']")));

        for(int i = 0; i < 10; i++) {
            idiDesnoButton.click();
            Thread.sleep(1000);
        }
    }

    @Test
    public void filtrirajIzdvojeneOglase() throws InterruptedException {
        WebElement filtrirajBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='px-md mb-lg flex items-center pt-lg justify-between']//button")));
        filtrirajBtn.click();

        WebElement kompjuteri = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='custom-list checkbox']//li[6]")));
        Thread.sleep(1500);
        kompjuteri.click();

        Thread.sleep(7000);
    }

    @Test
    public void posaljiPoruku() throws InterruptedException  {
        login();
        Thread.sleep(1000);
        webDriver.get("https://olx.ba/artikal/65424929#");
        Thread.sleep(1000);
        List<WebElement> poruka = webDriver.findElements(By.xpath("//button[.//text()[contains(., 'Poruka')]]"));
        Thread.sleep(1000);
        poruka.get(1).click();

        WebElement textarea = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='flex-auto']")));
        textarea.sendKeys("Test metode posaljiPoruku()");

        WebElement posaljiPorukuBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Pošalji poruku')]]")));
        posaljiPorukuBtn.click();

        Thread.sleep(3000);
    }

    @Test
    public void dodajUpit() throws InterruptedException {
        login();
        Thread.sleep(1000);
        webDriver.get("https://olx.ba/artikal/65424929#");

        WebElement textarea = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Postavi pitanje korisniku']")));
        textarea.sendKeys("Poštovanje, koji je SSD i koliko ima memorije?");

        WebElement btn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Postavi pitanje')]]")));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Thread.sleep(5000);
        js.executeScript("window.scrollBy(0,1000)");

        Thread.sleep(2000);
        btn.click();
        Thread.sleep(3000);
    }

    @Test
    public void filtirajOglase() throws InterruptedException {
        WebElement kategorijeLink = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/kategorije']")));
        kategorijeLink.click();

        WebElement kategorija = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/pretraga?category_id=18']"))); //
        kategorija.click();

        WebElement proizvodjac = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Proizvođač')]")));
        proizvodjac.click();

        Select markaAutoa = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='cursor-pointer']"))));
        Thread.sleep(1000);
        markaAutoa.selectByVisibleText("Mercedes-Benz");

        WebElement cijena = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Cijena')]")));
        Thread.sleep(1000);
        cijena.click();
        WebElement od = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='od']")));
        Thread.sleep(1000);
        od.sendKeys("10000");
        WebElement do_ = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='do']")));
        Thread.sleep(1000);
        do_.sendKeys("20000");

        WebElement lokacija = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Lokacija')]")));
        Thread.sleep(1000);
        lokacija.click();
        Select lokacija_ = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='text-base font-bold border-none rounded-lg py-2 px-md search-bg']"))));
        Thread.sleep(1000);
        lokacija_.selectByVisibleText("Kanton Sarajevo");

        WebElement godiste = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Godište')]")));
        Thread.sleep(1000);
        godiste.click();
        Select god_od =  new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='mr-sm']"))));
        Thread.sleep(1000);
        god_od.selectByVisibleText("2000");
        Select god_do = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='ml-sm']"))));
        Thread.sleep(1000);
        god_do.selectByVisibleText("2025");

        WebElement gorivo = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Gorivo')]")));
        Thread.sleep(1000);
        gorivo.click();
        WebElement dizel = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Dizel')]")));
        Thread.sleep(1000);
        dizel.click();
        WebElement benzin = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Benzin')]")));
        Thread.sleep(1000);
        benzin.click();

        WebElement transmisija = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Transmisija')]")));
        Thread.sleep(1000);
        transmisija.click();
        WebElement manuelni = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Manuelni')]")));
        Thread.sleep(1000);
        manuelni.click();

        WebElement kilometraza = webDriver.findElement(By.xpath("//*[contains(text(), 'Kilometraža')]"));
        Thread.sleep(1000);
        kilometraza.click();
        List<WebElement> kilo_od = webDriver.findElements(By.xpath("//input[@placeholder='od']"));
        Thread.sleep(1000);
        kilo_od.get(2).sendKeys("0");
        List<WebElement> kilo_do = webDriver.findElements(By.xpath("//input[@placeholder='do']"));
        Thread.sleep(1000);
        kilo_do.get(2).sendKeys("500000");

        WebElement brojVrata = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Broj vrata')]")));
        Thread.sleep(1000);
        brojVrata.click();
        WebElement brojVrata4_5 = webDriver.findElement(By.xpath("//*[contains(text(), '4/5')]"));
        Thread.sleep(1000);
        brojVrata4_5.click();

        Thread.sleep(10000);
    }

    @Test
    public void promijeniInfo() throws InterruptedException {
        login();
        WebElement profilLink = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/profil/SVVTProjekat558/aktivni']")));
        profilLink.click();

        WebElement postavke = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Postavke')]")));
        postavke.click();

        Thread.sleep(2000);
        List<WebElement> inputs = webDriver.findElements(By.xpath("//input[@type='text']"));
        Thread.sleep(1000);
        WebElement ime = inputs.get(0);
        ime.sendKeys("Kedy");
        Thread.sleep(1000);
        WebElement prezime = inputs.get(1);
        prezime.sendKeys("Muslic");

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        WebElement spasiIzmjene = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='mt-lg']")));
        Thread.sleep(1000);
        spasiIzmjene.click();

        Thread.sleep(2000);
    }

    @Test
    public void objaviOglas() throws InterruptedException {
        login();
        WebElement objaviOglas = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Objavi oglas')]")));
        objaviOglas.click();

        WebElement osatloBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='item-Objavite nešto drugo']")));
        osatloBtn.click();

        WebElement input = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='']")));
        input.sendKeys("Biciklo, povoljno");

        Thread.sleep(1000);

        WebElement nastavi = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Nastavi')]]")));
        nastavi.click();

        WebElement kategorija = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='kategorija']")));
        kategorija.click();

        Thread.sleep(10000);

        Select proizvodjac = new Select(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@label='Proizvođač']"))));
        Thread.sleep(1000);
        proizvodjac.selectByIndex(1);

        List<WebElement> switches = webDriver.findElements(By.xpath("//label[@class='switch']"));
        Thread.sleep(1000);
        switches.get(0).click();

        WebElement sljedeciKorak = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Sljedeći korak')]]")));
        Thread.sleep(1000);
        sljedeciKorak.click();

        WebElement bmxButton = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='buttonBMX']")));
        bmxButton.click();

        Thread.sleep(1000);

        WebElement unisex = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='buttonUnisex']")));
        unisex.click();

        Thread.sleep(1000);

        WebElement brojBrzina = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='broj-brzina']")));
        brojBrzina.sendKeys("21");

        Thread.sleep(1000);

        sljedeciKorak.click();

        Thread.sleep(1000);

        WebElement zavrsiObjavu = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Završi objavu oglasa')]]")));
        zavrsiObjavu.click();

        Thread.sleep(5000);
    }

    @Test
    public void obirsiOglas() throws InterruptedException {
        login();
        webDriver.get("https://olx.ba/mojolx/artikli/aktivni");

        List<WebElement> oglas = webDriver.findElements(By.xpath("//div[@class='w-full']"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(oglas.get(1)).perform();

        Thread.sleep(1000);

        WebElement zavrsiBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Završi')]]")));
        zavrsiBtn.click();

        Thread.sleep(1000);

        WebElement izbrisi = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='option-button delete']")));
        izbrisi.click();

        List<WebElement> da = webDriver.findElements(By.xpath("//div[@class='option-button delete']"));
        da.get(1).click();

        Thread.sleep(5000);
    }

    @Test
    public void editOglas() throws  InterruptedException {
        login();
        webDriver.get("https://olx.ba/mojolx/artikli/aktivni");

        List<WebElement> oglas = webDriver.findElements(By.xpath("//div[@class='w-full']"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(oglas.get(1)).perform();

        Thread.sleep(1000);

        WebElement urediBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Uredi')]]")));
        urediBtn.click();

        Thread.sleep(3000);

        WebElement input = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='w-full']")));
        input.sendKeys(", BMX");

        Thread.sleep(1000);

       WebElement muskiBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='buttonMuško']")));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(2000);
        muskiBtn.click();

        Thread.sleep(1000);


        WebElement zavrsiUredjivanjeBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Završi uređivanje')]]")));
        js.executeScript("arguments[0].scrollIntoView(true);", zavrsiUredjivanjeBtn);
        Thread.sleep(2000);
        zavrsiUredjivanjeBtn.click();

        Thread.sleep(3000);
    }
}
