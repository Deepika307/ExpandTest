package com.expandtesting.tests;

import com.expandtesting.framework.pages.AlertPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlertTests extends BaseTest {

    @Test
    public void verifyJavaScriptAlertAcceptFlow() {
        AlertPage alertPage = new AlertPage(driver).open().acceptJsAlert();
        Assert.assertEquals(alertPage.getResultText(), "OK",
                "Alert acceptance result should be displayed.");
    }

    @Test
    public void verifyJavaScriptConfirmDismissFlow() {
        AlertPage alertPage = new AlertPage(driver).open().dismissJsConfirm();
        Assert.assertEquals(alertPage.getResultText(), "Cancel",
                "Dismissed confirm result should be displayed.");
    }

    @Test
    public void verifyJavaScriptPromptAcceptFlow() {
        AlertPage alertPage = new AlertPage(driver).open().submitJsPrompt("Expand Testing Prompt");
        Assert.assertEquals(alertPage.getResultText(), "Expand Testing Prompt",
                "Prompt result should contain the entered text.");
    }
}
