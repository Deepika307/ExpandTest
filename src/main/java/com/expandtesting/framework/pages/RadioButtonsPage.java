package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RadioButtonsPage extends BasePage {

    @FindBy(xpath = "(//input[@type='radio'])[1]")
    private WebElement firstRadioButton;

    @FindBy(xpath = "(//input[@type='radio'])[2]")
    private WebElement secondRadioButton;

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Radio Buttons')]");

    public RadioButtonsPage(WebDriver driver) {
        super(driver);
    }

    public RadioButtonsPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/radio-buttons");
        waitForElement(pageHeader);
        return this;
    }

    public RadioButtonsPage selectFirstRadioButton() {
        jsClick(firstRadioButton);
        return this;
    }

    public RadioButtonsPage selectSecondRadioButton() {
        jsClick(secondRadioButton);
        return this;
    }

    public boolean isFirstSelected() {
        return firstRadioButton.isSelected();
    }

    public boolean isSecondSelected() {
        return secondRadioButton.isSelected();
    }
}
