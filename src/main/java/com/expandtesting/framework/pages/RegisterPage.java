package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage extends BasePage {

    @FindBy(xpath = "//input[@name='name' or @placeholder='Enter your name']")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@name='email' or @type='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='password' or @type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "(//input[@name='confirmPassword' or @type='password'])[last()]")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[normalize-space()='Register' or normalize-space()='Sign up']")
    private WebElement registerButton;

    private final By registerHeader = By.xpath("//*[contains(normalize-space(),'Create an account') or contains(normalize-space(),'Register')]");
    private final By toastMessage = By.xpath("//*[contains(@class,'alert') or contains(@class,'Toastify') or contains(@role,'alert')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/notes/app/register");
        waitForElement(registerHeader);
        return this;
    }

    public boolean isRegistrationSuccessful() {
        try {
            return new WebDriverWait(driver, timeout()).until(currentDriver ->
                    currentDriver.getCurrentUrl().contains("/login")
                            || currentDriver.findElements(toastMessage).stream().anyMatch(WebElement::isDisplayed));
        } catch (Exception exception) {
            return false;
        }
    }

    public RegisterPage register(String name, String email, String password, String confirmPassword) {
        type(nameInput, name);
        type(emailInput, email);
        type(passwordInput, password);
        type(confirmPasswordInput, confirmPassword);
        click(registerButton);
        return this;
    }

    public String getToastMessage() {
        return waitForElement(toastMessage).getText().trim();
    }
}
