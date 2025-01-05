import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Cart {
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
}
