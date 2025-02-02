package com.testinium.step;

import com.testinium.base.BaseTest;
import com.testinium.model.ElementInfo;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseStepsTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public BaseStepsTest() {
        this.driver = BaseTest.getDriver(); // **Singleton WebDriver kullanımı**
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }


    @Step("Sayfaya git <url>")
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Step("<key> elementine tıkla")
    public void clickElement(String key) {
        WebElement element = findElement(key);
        element.click();
    }

    @Step("<key> alanına <text> yaz")
    public void enterText(String key, String text) {
        WebElement element = findElement(key);
        element.clear();
        element.sendKeys(text);
    }


    @Step("<key> elementi görünene kadar bekle")
    public void waitForElement(String key) {
        WebElement element = findElement(key);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    @Step("<key> elementi görüntülendiğini doğrula")
    public void verifyElementIsDisplayed(String key) {
        WebElement element = findElement(key);
        assertTrue(element.isDisplayed(), "Element görünmüyor: " + key);
    }

    @Step("Sayfa yüklenme süresini kontrol et <url>")
    public void checkPageLoadTime(String url) {
        long startTime = System.currentTimeMillis();
        driver.get(url);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;

        System.out.println("Sayfa yüklenme süresi: " + loadTime + " ms");
        assertTrue(loadTime < 3000, "Sayfa yüklenme süresi çok uzun!");
    }

    @Step("<int> saniye beklenir ve <key> xpath elementi <condition> olana kadar beklenir")
    public void waitForElementCondition(int seconds, String key, String condition) {
        // Dinamik bekleme için WebDriverWait kullanıyoruz
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

        WebElement element = null;

        // Beklenecek koşula göre işlem yapıyoruz
        switch (condition.toLowerCase()) {
            case "görünür":
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(key)));
                break;
            case "tıklanabilir":
                element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
                break;
            case "var":
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(key)));
                break;
            default:
                throw new IllegalArgumentException("Geçersiz koşul: " + condition);
        }

        // Koşul sağlandığında işlemi yap
        if (element != null) {
            System.out.println("Element " + condition + " durumda.");
        }
    }



@Step("<int> saniye boyunca element görünene kadar bekle")
    public void waitForElementWithDynamicWait(int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("element-id")));
    }




    private WebElement findElement(String key) {
        ElementInfo elementInfo = BaseTest.getElementInfo(key);
        By by = getByType(elementInfo);
        return driver.findElement(by);
    }

    private By getByType(ElementInfo elementInfo) {
        switch (elementInfo.getType()) {
            case "id":
                return By.id(elementInfo.getValue());
            case "name":
                return By.name(elementInfo.getValue());
            case "xpath":
                return By.xpath(elementInfo.getValue());
            case "css":
                return By.cssSelector(elementInfo.getValue());
            case "class":
                return By.className(elementInfo.getValue());
            default:
                throw new IllegalArgumentException("Bilinmeyen locator türü: " + elementInfo.getType());
        }
    }
}
