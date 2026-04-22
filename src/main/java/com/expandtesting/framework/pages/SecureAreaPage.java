package com.expandtesting.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SecureAreaPage extends BasePage {

    @FindBy(xpath = "//a[contains(@href,'logout')] | //button[normalize-space()='Logout']")
    private WebElement logoutButton;

    private final By successMessage = By.id("flash");

    public SecureAreaPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSecurePageLoaded() {
        waitForUrlContains("/secure");
        return isDisplayed(successMessage);
    }

    public String getSuccessMessage() {
        return waitForElement(successMessage).getText().trim();
    }

    public AuthLoginPage logout() {
        click(logoutButton);
        return new AuthLoginPage(driver);
    }
}
