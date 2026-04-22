package com.expandtesting.framework.pages;

import com.expandtesting.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropCirclesPage extends BasePage {

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Drag and Drop Circles')]");
    private final By targetBucket = By.id("target");
    private final By sourceBucket = By.id("source");

    public DragAndDropCirclesPage(WebDriver driver) {
        super(driver);
    }

    public DragAndDropCirclesPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/drag-and-drop-circles");
        waitForElement(pageHeader);
        return this;
    }

    public DragAndDropCirclesPage dragCircleToBucket(String color) {
        WebElement draggableCircle = waitForElement(By.cssSelector("#source ." + color));
        WebElement dropTarget = waitForElement(targetBucket);
        new Actions(driver)
                .dragAndDrop(draggableCircle, dropTarget)
                .perform();
        return this;
    }

    public boolean isCircleInsideTarget(String color) {
        return !driver.findElements(By.cssSelector("#target ." + color)).isEmpty();
    }

    public boolean isCircleStillInSource(String color) {
        return !driver.findElements(By.cssSelector("#source ." + color)).isEmpty();
    }
}
