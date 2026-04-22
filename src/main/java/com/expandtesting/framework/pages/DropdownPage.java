package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DropdownPage extends BasePage {

    @FindBy(id = "dropdown")
    private WebElement dropdown;

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Dropdown')]");

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public DropdownPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/dropdown");
        waitForElement(pageHeader);
        return this;
    }

    public DropdownPage selectOption(String option) {
        selectByVisibleText(dropdown, option);
        return this;
    }

    public String getSelectedOption() {
        return new org.openqa.selenium.support.ui.Select(dropdown).getFirstSelectedOption().getText().trim();
    }
}
