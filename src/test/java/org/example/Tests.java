package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {

    @Test
    public void DLUserManual() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        // 1. Зайти на сайт Яндекса (https://www.yandex.ru/);
        driver.get("https://www.yandex.ru/");
        // Проверяем, что при переходе на сайт Яндекса происходит редирект на сайт https://dzen.ru/?yredirect=true
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://dzen.ru/?yredirect=true", "Не произошла автоматическая переадресация на страницу dzen.ru");

        // Переходим на фрейм, в котором лежит элемент строки поиска
        driver.switchTo().frame(0);

        // 2. В поисковую строку ввести «Газинформсервис» (без кавычек);
        WebElement input = driver.findElement(By.xpath("//input[@name='text']"));
        input.sendKeys("Газинформсервис");

        // Запускаем поиск
        WebElement searchBtn = driver.findElement(By.xpath("//button[@class='arrow__button' and @type='submit']"));
        searchBtn.click();

        // Ожидаем прогрузки страницы и появления попапа "Сделайте Яндекс основным поиском"
        Thread.sleep(2000);

        // Создаём список вкладок и переходим на втроую вкладку с результатами поиска
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        // Проверяем, появляется ли попап "Сделайте Яндекс основным поиском"
        List<WebElement> noThanks = driver.findElements(By.xpath("//button[@title='Нет, спасибо']"));
        boolean isPresent = !noThanks.isEmpty();
        // Если попап появляется, то закрываем его
        if (isPresent){
            noThanks.get(0).click();
        }

        // 3. Среди результатов поиска найти тот, который соответствует сайту https://www.gaz-is.ru/;
        WebElement site = driver.findElement(By.xpath("//ul[@id='search-result']//a[@href='https://www.gaz-is.ru/']"));
        // 4. Перейти по ссылке с найденного результата;
        site.click();

        // Обновляем список вкладок и переключаемся на третью вкладку с сайтом gaz-is
        tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));

        // 5. На сайте кликнуть по меню «Продукты» и в отображённом списке продуктов кликнуть по ссылке на продукте «Ankey IDM»;
        WebElement products = driver.findElement(By.xpath("//a[contains(.,'Продукты')]"));
        products.click();

        // Ожидаем открытия списка продуктов
        Thread.sleep(1000);
        // Ищем продукт Ankey IDM и переходим по ссылке
        WebElement ankey = driver.findElement(By.xpath("//a[contains(.,'Ankey IDM')]"));
        ankey.click();

        // 6. Убедиться, что загрузилась страница с информацией о продукте;
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gaz-is.ru/produkty/upravlenie-ib/ankey-idm", "Страница с информацией о продукте Ankey IDM не загрузилась");

        // 7. Скачать руководство пользователя Ankey IDM.
        // Переходим на вкладку "Материалы"
        WebElement material = driver.findElement(By.xpath("//a[@title='Материалы']"));
        material.click();

        // Скачиваем руководство пользователя
        WebElement dlManual = driver.findElement(By.xpath("//a[contains(.,'Руководство пользователя Ankey IDM')]"));
        dlManual.click();

        driver.quit();

    }
}