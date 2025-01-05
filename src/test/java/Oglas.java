import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Oglas {
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
    public void filtrirajIzdvojeneOglase() throws InterruptedException {
        WebElement filtrirajBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='px-md mb-lg flex items-center pt-lg justify-between']//button")));
        filtrirajBtn.click();

        WebElement kompjuteri = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='custom-list checkbox']//li[6]")));
        Thread.sleep(1500);
        kompjuteri.click();

        Thread.sleep(7000);
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
    public void filtrirajOglaseCijena() throws InterruptedException {
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
        od.sendKeys("20000");
        WebElement do_ = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='do']")));
        Thread.sleep(1000);
        do_.sendKeys("10000");

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
    public void objaviOglasFail() throws InterruptedException {
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

        /*List<WebElement> switches = webDriver.findElements(By.xpath("//label[@class='switch']"));
        Thread.sleep(1000);
        switches.get(0).click();*/

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
}
