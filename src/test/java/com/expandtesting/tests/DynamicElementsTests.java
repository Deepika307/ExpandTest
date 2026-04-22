package com.expandtesting.tests;

import com.expandtesting.framework.pages.DragAndDropCirclesPage;
import com.expandtesting.framework.pages.DynamicLoadingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DynamicElementsTests extends BaseTest {

    @Test
    public void verifyDynamicLoadingExample() {
        DynamicLoadingPage dynamicLoadingPage = new DynamicLoadingPage(driver)
                .openExampleOne()
                .startDynamicLoad();

        Assert.assertTrue(dynamicLoadingPage.getLoadedText().contains("Hello World!"),
                "Dynamically loaded text should appear after the loader disappears.");
    }

    @Test
    public void verifyDragAndDropCircles() {
        DragAndDropCirclesPage dragAndDropCirclesPage = new DragAndDropCirclesPage(driver)
                .open()
                .dragCircleToBucket("red");

        Assert.assertTrue(dragAndDropCirclesPage.isCircleInsideTarget("red"),
                "Dragged circle should move into the target bucket.");
        Assert.assertFalse(dragAndDropCirclesPage.isCircleStillInSource("red"),
                "Dragged circle should no longer remain in the source area.");
    }
}
