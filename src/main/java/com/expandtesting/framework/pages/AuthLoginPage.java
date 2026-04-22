package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthLoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    private final By flashMessage = By.id("flash");
    private final By loginCard = By.xpath("//h1[contains(normalize-space(),'Test Login page')]");

    public AuthLoginPage(WebDriver driver) {
        super(driver);
    }

    public AuthLoginPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        waitForElement(loginCard);
        return this;
    }

    public AuthLoginPage login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return this;
    }

    public SecureAreaPage loginWithValidUser(String username, String password) {
        login(username, password);
        return new SecureAreaPage(driver);
    }

    public String getFlashMessage() {
        return waitForElement(flashMessage).getText().trim();
    }

    public boolean isLoaded() {
        return isDisplayed(loginCard);
    }
}
