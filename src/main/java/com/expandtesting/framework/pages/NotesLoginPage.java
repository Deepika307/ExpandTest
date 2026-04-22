package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotesLoginPage extends BasePage {

    @FindBy(xpath = "//input[@name='email' or @type='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='password' or @type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[normalize-space()='Login' or normalize-space()='Sign in']")
    private WebElement loginButton;

    private final By pageHeader = By.xpath("//*[contains(normalize-space(),'Login') or contains(normalize-space(),'Sign in')]");

    public NotesLoginPage(WebDriver driver) {
        super(driver);
    }

    public NotesLoginPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/notes/app/login");
        waitForElement(pageHeader);
        return this;
    }

    public NotesPage login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
        return new NotesPage(driver);
    }
}
