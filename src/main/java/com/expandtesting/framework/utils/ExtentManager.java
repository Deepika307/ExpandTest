package com.expandtesting.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            try {
                Path reportDirectory = Paths.get("reports");
                Files.createDirectories(reportDirectory);
                ExtentSparkReporter sparkReporter =
                        new ExtentSparkReporter(reportDirectory.resolve("extent-report.html").toString());
                sparkReporter.config().setReportName("Expand Testing Automation Report");
                sparkReporter.config().setDocumentTitle("Selenium Java Framework Report");

                extentReports = new ExtentReports();
                extentReports.attachReporter(sparkReporter);
                extentReports.setSystemInfo("Framework", "Selenium + Java + TestNG");
                extentReports.setSystemInfo("Application", "practice.expandtesting.com");
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to initialize ExtentReports.", exception);
            }
        }
        return extentReports;
    }
}
