package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckboxPage extends BasePage {

    @FindBy(xpath = "(//input[@type='checkbox'])[1]")
    private WebElement checkboxOne;

    @FindBy(xpath = "(//input[@type='checkbox'])[2]")
    private WebElement checkboxTwo;

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Checkbox') or contains(normalize-space(),'Check Boxes')]");

    public CheckboxPage(WebDriver driver) {
        super(driver);
    }

    public CheckboxPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/checkboxes");
        waitForElement(pageHeader);
        return this;
    }

    public CheckboxPage toggleFirstCheckbox() {
        click(checkboxOne);
        return this;
    }

    public CheckboxPage toggleSecondCheckbox() {
        click(checkboxTwo);
        return this;
    }

    public boolean isFirstCheckboxSelected() {
        return checkboxOne.isSelected();
    }

    public boolean isSecondCheckboxSelected() {
        return checkboxTwo.isSelected();
    }
}
