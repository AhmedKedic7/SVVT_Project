import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class User {
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
    public void posaljiPoruku() throws InterruptedException  {
        login();
        Thread.sleep(1000);
        webDriver.get("https://olx.ba/artikal/65424929#");
        Thread.sleep(1000);
        List<WebElement> poruka = webDriver.findElements(By.xpath("//button[.//text()[contains(., 'Poruka')]]"));
        Thread.sleep(1000);
        poruka.get(1).click();

        WebElement textarea = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='flex-auto']")));
        textarea.sendKeys("This is an automated message.");

        WebElement posaljiPorukuBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Pošalji poruku')]]")));
        posaljiPorukuBtn.click();

        WebElement korisnik = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/profil/kedi77ahmed/aktivni']")));

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Uspješno ste poslali poruku korisniku " + korisnik.getText(), toastr.getText());

        Thread.sleep(3000);
    }

    @Test
    public void posaljiPorukuFail() throws InterruptedException  {
        login();
        Thread.sleep(1000);
        webDriver.get("https://olx.ba/artikal/65424929#");
        Thread.sleep(1000);
        List<WebElement> poruka = webDriver.findElements(By.xpath("//button[.//text()[contains(., 'Poruka')]]"));
        Thread.sleep(1000);
        poruka.get(1).click();

        WebElement textarea = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='flex-auto']")));
        textarea.sendKeys("");

        WebElement posaljiPorukuBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Pošalji poruku')]]")));
        posaljiPorukuBtn.click();

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Podaci koje ste poslali nisu potpuni ili su u neispravnom formatu.", toastr.getText());

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
    public void dodajUpitFail() throws InterruptedException {
        login();
        Thread.sleep(1000);
        webDriver.get("https://olx.ba/artikal/65424929#");

        WebElement textarea = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Postavi pitanje korisniku']")));
        textarea.sendKeys("");

        WebElement btn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Postavi pitanje')]]")));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Thread.sleep(5000);
        js.executeScript("window.scrollBy(0,750)");

        Thread.sleep(2000);
        btn.click();

        List<WebElement> div = webDriver.findElements(By.xpath("//div[@class='flex items-start w-full']/div/div/div/div/p"));
        assertEquals("Niste upisali pitanje!", div.get(1).getText());

        Thread.sleep(3000);
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
        ime.clear();
        ime.sendKeys("Kedy");
        Thread.sleep(1000);
        WebElement prezime = inputs.get(1);
        prezime.clear();
        prezime.sendKeys("Muslic");

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        WebElement spasiIzmjene = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='mt-lg']")));
        Thread.sleep(1000);
        spasiIzmjene.click();

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Uspješno ste izmjenili profil.", toastr.getText());

        Thread.sleep(2000);
    }

    @Test
    public void promijeniInfoFail() throws InterruptedException {
        login();
        WebElement profilLink = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/profil/SVVTProjekat558/aktivni']")));
        profilLink.click();

        WebElement postavke = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Postavke')]")));
        postavke.click();

        Thread.sleep(2000);
        List<WebElement> inputs = webDriver.findElements(By.xpath("//input[@type='text']"));
        Thread.sleep(1000);

        WebElement mob = inputs.get(2);
        mob.sendKeys("387671091841111");


        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        WebElement spasiIzmjene = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='mt-lg']")));
        Thread.sleep(1000);
        spasiIzmjene.click();

        WebElement toastr = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("v-toast__text")));
        assertEquals("Unešeni broj nije u validnom formatu.", toastr.getText());

        Thread.sleep(2000);
    }

    @Test
    public void dodajOlxKrediteFail() throws InterruptedException {
        login();

        WebElement stanjeNaloga = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='stanje naloga']")));
        Thread.sleep(1000);
        stanjeNaloga.click();

        WebElement dopuniOlxKreditBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='mt-lg']")));
        dopuniOlxKreditBtn.click();

        WebElement kreditBtn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn-credit']")));
        Thread.sleep(1000);
        kreditBtn.click();

        Thread.sleep(1000);

        List<WebElement> inputs = webDriver.findElements(By.xpath("//input"));

        WebElement nazivFirme = inputs.get(1);
        nazivFirme.sendKeys("Jaka Firma d.o.o");
        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(1000);
        WebElement btn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//text()[contains(., 'Plati OLX kredit')]]")));
        btn.click();

        WebElement error = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='flex mb-sm flex-col items-start justify-start']/p")));
        assertEquals("Sva polja su obavezna.", error.getText());
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
