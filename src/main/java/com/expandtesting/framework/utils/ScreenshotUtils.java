package com.expandtesting.framework.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String testName) {
        try {
            Path screenshotDirectory = Paths.get("screenshots");
            Files.createDirectories(screenshotDirectory);
            String timestamp = LocalDateTime.now().format(FORMATTER);
            Path targetPath = screenshotDirectory.resolve(testName + "_" + timestamp + ".png");
            Path source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(source, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toAbsolutePath().toString();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to capture screenshot for test: " + testName, exception);
        }
    }
}
