package com.expandtesting.framework.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotesPage extends BasePage {

    @FindBy(xpath = "//button[contains(normalize-space(),'Add Note') or normalize-space()='Add New Note' or normalize-space()='Create Note' or @aria-label='Add']")
    private WebElement addNoteButton;

    @FindBy(xpath = "//input[@name='title' or @placeholder='Title']")
    private WebElement titleInput;

    @FindBy(xpath = "//textarea[@name='description' or @placeholder='Description']")
    private WebElement descriptionInput;

    @FindBy(xpath = "//select[@name='category' or @id='category']")
    private WebElement categoryDropdown;

    @FindBy(xpath = "//button[normalize-space()='Create' or normalize-space()='Save' or normalize-space()='Update']")
    private WebElement saveNoteButton;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or @type='search']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'Logout') or contains(normalize-space(),'Sign out')]")
    private WebElement logoutButton;

    private final By notesPageHeader = By.xpath("//*[contains(normalize-space(),'My Notes') or contains(normalize-space(),'Notes')]");
    private final By noteCards = By.xpath(
            "//*[contains(@class,'note-card') or @data-testid='note-card' or .//button[contains(normalize-space(),'Delete')]]");
    private final By toastMessage = By.xpath("//*[contains(@class,'alert') or contains(@class,'Toastify') or contains(@role,'alert')]");
    private final By loadingIndicator = By.xpath("//*[contains(@class,'spinner') or contains(normalize-space(),'Loading')]");

    public NotesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForElement(notesPageHeader);
        pauseForReactRender();
        return true;
    }

    public NotesPage createNote(String title, String description, String category) {
        click(addNoteButton);
        type(titleInput, title);
        type(descriptionInput, description);
        selectByVisibleText(categoryDropdown, category);
        click(saveNoteButton);
        waitForOptionalLoader();
        return this;
    }

    public NotesPage searchNote(String query) {
        type(searchInput, query);
        click(searchButton);
        pauseForReactRender();
        return this;
    }

    public NotesPage editNote(String existingTitle, String updatedTitle, String updatedDescription) {
        searchNote(existingTitle);
        click(findActionButton(existingTitle, "Edit"));
        type(titleInput, updatedTitle);
        type(descriptionInput, updatedDescription);
        click(saveNoteButton);
        waitForOptionalLoader();
        return this;
    }

    public NotesPage deleteNote(String title) {
        searchNote(title);
        click(findActionButton(title, "Delete"));
        By confirmDelete = By.xpath("//div[@role='dialog']//button[normalize-space()='Delete' or normalize-space()='Confirm']");
        if (!driver.findElements(confirmDelete).isEmpty()) {
            click(waitForElement(confirmDelete));
        }
        waitForOptionalLoader();
        return this;
    }

    public NotesPage filterByCategory(String category) {
        click(waitForElement(By.xpath("//button[contains(normalize-space(),'" + category + "')]")));
        waitForOptionalLoader();
        return this;
    }

    public boolean isNotePresent(String text) {
        searchNote(text);
        By noteLocator = By.xpath(
                "//*[contains(normalize-space(),\"" + text + "\")]/ancestor::*[self::div or self::article or self::li]"
                        + "[.//*[self::button or self::a][contains(normalize-space(),'Edit') or contains(normalize-space(),'Delete')]][1]");
        return driver.findElements(noteLocator).stream()
                .anyMatch(WebElement::isDisplayed);
    }

    public boolean areVisibleNotesInCategory(String category) {
        List<WebElement> notes = driver.findElements(noteCards);
        if (notes.isEmpty()) {
            return false;
        }
        return notes.stream()
                .filter(WebElement::isDisplayed)
                .allMatch(note -> note.getText().toLowerCase().contains(category.toLowerCase()));
    }

    public String getToastMessage() {
        return waitForElement(toastMessage).getText().trim();
    }

    public NotesLoginPage logout() {
        click(logoutButton);
        return new NotesLoginPage(driver);
    }

    private WebElement findActionButton(String noteTitle, String actionText) {
        By rowAction = By.xpath(
                "//*[contains(normalize-space(),\"" + noteTitle + "\")]/ancestor::*[self::div or self::article or self::li][1]" +
                        "//*[self::button or self::a][contains(normalize-space(),'" + actionText + "')]");
        return waitForElement(rowAction);
    }

    private void waitForOptionalLoader() {
        if (!driver.findElements(loadingIndicator).isEmpty()) {
            waitForInvisibility(loadingIndicator);
        }
        pauseForReactRender();
    }
}
