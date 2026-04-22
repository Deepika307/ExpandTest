package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertPage extends BasePage {

    @FindBy(id = "js-alert")
    private WebElement jsAlertButton;

    @FindBy(id = "js-confirm")
    private WebElement jsConfirmButton;

    @FindBy(id = "js-prompt")
    private WebElement jsPromptButton;

    private final By resultText = By.id("dialog-response");
    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'JavaScript Dialogs page')]");

    public AlertPage(WebDriver driver) {
        super(driver);
    }

    public AlertPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/js-dialogs");
        waitForElement(pageHeader);
        return this;
    }

    public AlertPage acceptJsAlert() {
        click(jsAlertButton);
        Alert alert = waits.explicitWait().until(ExpectedConditions.alertIsPresent());
        alert.accept();
        return this;
    }

    public AlertPage dismissJsConfirm() {
        click(jsConfirmButton);
        Alert alert = waits.explicitWait().until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        return this;
    }

    public AlertPage submitJsPrompt(String text) {
        click(jsPromptButton);
        Alert alert = waits.explicitWait().until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        alert.accept();
        return this;
    }

    public String getResultText() {
        return waitForElement(resultText).getText().trim();
    }
}
