package com.expandtesting.framework.utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private final WebDriver driver;
    private final Duration timeout;

    public WaitUtils(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    public WebDriverWait explicitWait() {
        return new WebDriverWait(driver, timeout);
    }

    public Wait<WebDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(RuntimeException.class);
    }

    public WebElement waitUntilVisible(WebElement element) {
        return explicitWait().until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitUntilClickable(WebElement element) {
        return explicitWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitUntilInvisible(WebElement element) {
        return explicitWait().until(ExpectedConditions.invisibilityOf(element));
    }
}
