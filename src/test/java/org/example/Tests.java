package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Tests {

    @Test
    public void DLUserManual() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.yandex.ru/");
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://dzen.ru/?yredirect=true", "Не произошёл редирект на страницу dzen.ru");

        driver.switchTo().frame(0);
        WebElement input = driver.findElement(By.xpath("//input[@name='text']"));
        input.sendKeys("Газинформсервис");

        WebElement searchBtn = driver.findElement(By.xpath("//button[@class='arrow__button' and @type='submit']"));
        searchBtn.click();

        Thread.sleep(2000);

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement noThanks = driver.findElement(By.xpath("//button[@title='Нет, спасибо']"));
        noThanks.click();

        WebElement site = driver.findElement(By.xpath("//ul[@id='search-result']//a[@href='https://www.gaz-is.ru/']"));
        site.click();

        tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));

        WebElement products = driver.findElement(By.xpath("//a[contains(.,'Продукты')]"));
        products.click();

        Thread.sleep(2000);

        WebElement ankey = driver.findElement(By.xpath("//a[contains(.,'Ankey IDM')]"));
        ankey.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gaz-is.ru/produkty/upravlenie-ib/ankey-idm", "Страница с информацией о продукте Ankey IDM не загрузилась");

        WebElement material = driver.findElement(By.xpath("//a[@title='Материалы']"));
        material.click();

        WebElement dlManual = driver.findElement(By.xpath("//a[contains(.,'Руководство пользователя Ankey IDM')]"));
        dlManual.click();

    }
}