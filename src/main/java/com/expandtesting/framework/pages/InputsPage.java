package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InputsPage extends BasePage {

    @FindBy(id = "input-number")
    private WebElement numberInput;

    @FindBy(id = "input-text")
    private WebElement textInput;

    @FindBy(id = "input-password")
    private WebElement passwordInput;

    @FindBy(id = "input-date")
    private WebElement dateInput;

    @FindBy(id = "btn-display-inputs")
    private WebElement displayInputsButton;

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Web inputs')]");
    private final By displayedInputsMessage = By.id("result");
    private final By outputDate = By.id("output-date");

    public InputsPage(WebDriver driver) {
        super(driver);
    }

    public InputsPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/inputs");
        waitForElement(pageHeader);
        return this;
    }

    public InputsPage enterInputValues(String number, String text, String password, String date) {
        type(numberInput, number);
        type(textInput, text);
        type(passwordInput, password);
        setValueByJavaScript(dateInput, date);
        return this;
    }

    public InputsPage clickDisplayInputs() {
        click(displayInputsButton);
        return this;
    }

    public String getNumberValue() {
        return numberInput.getAttribute("value");
    }

    public String getTextValue() {
        return textInput.getAttribute("value");
    }

    public String getPasswordValue() {
        return passwordInput.getAttribute("value");
    }

    public String getDateValue() {
        return waitForElement(outputDate).getText().trim();
    }

    public boolean areInputsDisplayed() {
        return isDisplayed(displayedInputsMessage);
    }
}
