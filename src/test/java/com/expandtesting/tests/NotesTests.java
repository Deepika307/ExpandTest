package com.expandtesting.tests;

import com.expandtesting.framework.config.ConfigReader;
import com.expandtesting.framework.pages.NotesLoginPage;
import com.expandtesting.framework.pages.NotesPage;
import com.expandtesting.framework.pages.RegisterPage;
import com.expandtesting.framework.utils.ExcelUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.SkipException;

public class NotesTests extends BaseTest {

    private String displayName;
    private String email;
    private String password;

    @BeforeClass(alwaysRun = true)
    public void loadRegistrationData() {
        Object[][] registrationData;
        try {
            registrationData = ExcelUtils.getSheetData(
                    ConfigReader.getRegisterDataPath(),
                    ConfigReader.getRegisterDataSheet());
        } catch (Exception exception) {
            throw new SkipException("Skipping notes tests until the registration Excel file is available: "
                    + exception.getMessage());
        }
        if (registrationData.length == 0) {
            throw new SkipException("Skipping notes tests because the configured signup sheet has no data.");
        }

        Object[] firstRow = registrationData[0];
        String uniqueSuffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String username = firstRow[0].toString().trim();
        password = firstRow[1].toString().trim();

        displayName = username + " User";
        email = username + "." + uniqueSuffix + "@example.com";
    }

    @Test(priority = 1)
    public void registerUserForNotesApp() {
        RegisterPage registerPage = new RegisterPage(driver).open();
        registerPage.register(displayName, email, password, password);
        Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                "Successful registration feedback should be visible.");
    }

    @Test(priority = 2, dependsOnMethods = "registerUserForNotesApp")
    public void createEditDeleteAndFilterNotes() {
        NotesPage notesPage = new NotesLoginPage(driver)
                .open()
                .login(email, password);

        String originalTitle = "Class Note " + System.currentTimeMillis();
        String updatedTitle = originalTitle + " Updated";
        String category = "Home";

        Assert.assertTrue(notesPage.isLoaded(), "Notes page should load after login.");

        notesPage.createNote(originalTitle, "Initial description for automation validation.", category);
        Assert.assertTrue(notesPage.isNotePresent(originalTitle), "Created note should appear in the notes list.");

        notesPage.editNote(originalTitle, updatedTitle, "Updated description for automation validation.");
        Assert.assertTrue(notesPage.isNotePresent(updatedTitle), "Updated note should appear after edit.");

        notesPage.filterByCategory(category);
        Assert.assertTrue(notesPage.isNotePresent(updatedTitle),
                "The updated note should remain visible after filtering by its category.");

        notesPage.deleteNote(updatedTitle);
        Assert.assertFalse(notesPage.isNotePresent(updatedTitle), "Deleted note should not appear in the notes list.");
    }
}
