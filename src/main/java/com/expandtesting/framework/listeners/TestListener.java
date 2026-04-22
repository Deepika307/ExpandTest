package com.expandtesting.framework.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import com.expandtesting.framework.driver.DriverFactory;
import com.expandtesting.framework.utils.ExtentManager;
import com.expandtesting.framework.utils.ScreenshotUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class TestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getRealClass().getSimpleName() + " :: "
                + result.getMethod().getMethodName();
        ExtentTest test = ExtentManager.getInstance().createTest(testName);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        test.info("Started test method: " + result.getMethod().getQualifiedName());

        Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
        if (testAnnotation != null && !testAnnotation.description().isBlank()) {
            test.info("Scenario: " + testAnnotation.description());
        }

        String parameters = formatParameters(result);
        if (!parameters.isBlank()) {
            test.info("Input data: " + parameters);
        }
        EXTENT_TEST.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (EXTENT_TEST.get() != null) {
            EXTENT_TEST.get().log(Status.PASS, "Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = EXTENT_TEST.get();
        if (test == null) {
            test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
            EXTENT_TEST.set(test);
        }

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.FAIL, "Failure reason: " + throwable.getMessage());
            test.log(Status.FAIL, "<details><summary>Full stack trace</summary><pre>" 
                    + escapeHtml(getStackTrace(throwable)) + "</pre></details>");
        } else {
            test.log(Status.FAIL, "Test failed without an exception message.");
        }

        try {
            String screenshotPath = ScreenshotUtils.capture(DriverFactory.getDriver(), result.getMethod().getMethodName());
            test.fail("Screenshot on failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception exception) {
            test.log(Status.WARNING, "Screenshot capture failed: " + exception.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (EXTENT_TEST.get() != null) {
            EXTENT_TEST.get().log(Status.SKIP, "Test skipped: " + result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
        EXTENT_TEST.remove();
    }

    private String formatParameters(ITestResult result) {
        Object[] parameters = result.getParameters();
        if (parameters == null || parameters.length == 0) {
            return "";
        }

        return Arrays.stream(parameters)
                .map(parameter -> parameter == null ? "null" : parameter.toString())
                .collect(Collectors.joining(" | "));
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    private String escapeHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
