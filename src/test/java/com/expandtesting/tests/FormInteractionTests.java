package com.expandtesting.tests;

import com.expandtesting.framework.pages.CheckboxPage;
import com.expandtesting.framework.pages.DropdownPage;
import com.expandtesting.framework.pages.InputsPage;
import com.expandtesting.framework.pages.RadioButtonsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FormInteractionTests extends BaseTest {

    @Test
    public void verifyInputsAcceptValues() {
        InputsPage inputsPage = new InputsPage(driver)
                .open()
                .enterInputValues("42", "ExpandTesting", "SafePassword123!", "2026-04-22")
                .clickDisplayInputs();

        Assert.assertEquals(inputsPage.getNumberValue(), "42", "Number input should retain the entered value.");
        Assert.assertEquals(inputsPage.getTextValue(), "ExpandTesting", "Text input should retain the entered value.");
        Assert.assertEquals(inputsPage.getPasswordValue(), "SafePassword123!", "Password input should retain the entered value.");
        Assert.assertEquals(inputsPage.getDateValue(), "2026-04-22", "Date input should retain the entered value.");
        Assert.assertTrue(inputsPage.areInputsDisplayed(), "Displayed inputs summary should appear after submission.");
    }

    @Test
    public void verifyDropdownSelection() {
        DropdownPage dropdownPage = new DropdownPage(driver)
                .open()
                .selectOption("Option 2");

        Assert.assertEquals(dropdownPage.getSelectedOption(), "Option 2",
                "Selected dropdown option should match the chosen value.");
    }

    @Test
    public void verifyCheckboxInteraction() {
        CheckboxPage checkboxPage = new CheckboxPage(driver).open();
        boolean initialState = checkboxPage.isFirstCheckboxSelected();

        checkboxPage.toggleFirstCheckbox();
        Assert.assertNotEquals(checkboxPage.isFirstCheckboxSelected(), initialState,
                "First checkbox state should change after the first toggle.");

        checkboxPage.toggleFirstCheckbox();
        Assert.assertEquals(checkboxPage.isFirstCheckboxSelected(), initialState,
                "First checkbox should return to its original state after the second toggle.");
    }

    @Test
    public void verifyRadioButtonSingleSelection() {
        RadioButtonsPage radioButtonsPage = new RadioButtonsPage(driver).open();

        radioButtonsPage.selectFirstRadioButton();
        Assert.assertTrue(radioButtonsPage.isFirstSelected(), "First radio button should be selected.");

        radioButtonsPage.selectSecondRadioButton();
        Assert.assertTrue(radioButtonsPage.isSecondSelected(), "Second radio button should be selected.");
        Assert.assertFalse(radioButtonsPage.isFirstSelected(),
                "Only one radio button should remain selected at a time.");
    }
}
