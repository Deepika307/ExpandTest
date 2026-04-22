package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import com.expandtesting.framework.utils.WaitUtils;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils waits;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver, ConfigReader.getTimeout());
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        WebElement clickableElement = waits.waitUntilClickable(element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                clickableElement);
        try {
            clickableElement.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException exception) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableElement);
        }
    }

    protected void type(WebElement element, String value) {
        WebElement visibleElement = waits.waitUntilVisible(element);
        visibleElement.clear();
        visibleElement.sendKeys(value);
    }

    protected void setValueByJavaScript(WebElement element, String value) {
        waits.waitUntilVisible(element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                        + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                element, value);
    }

    protected String readText(WebElement element) {
        return waits.waitUntilVisible(element).getText().trim();
    }

    protected void selectByVisibleText(WebElement element, String value) {
        waits.waitUntilVisible(element);
        new Select(element).selectByVisibleText(value);
    }

    protected WebElement waitForElement(By locator) {
        return waits.explicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForElements(By locator) {
        return waits.explicitWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void waitForText(By locator, String text) {
        waits.explicitWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    protected void waitForUrlContains(String fragment) {
        waits.explicitWait().until(ExpectedConditions.urlContains(fragment));
    }

    protected void waitForInvisibility(By locator) {
        waits.explicitWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean isDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }

    protected void jsClick(WebElement element) {
        waits.waitUntilVisible(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void pauseForReactRender() {
        waits.fluentWait().until(currentDriver ->
                ((JavascriptExecutor) currentDriver).executeScript("return document.readyState").equals("complete"));
    }

    protected Duration timeout() {
        return ConfigReader.getTimeout();
    }
}
