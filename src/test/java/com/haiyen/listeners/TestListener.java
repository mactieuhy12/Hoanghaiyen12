package com.haiyen.listeners;

import com.haiyen.helpers.CaptureHelper;
import com.haiyen.helpers.PropertiesHelper;
import com.haiyen.keywords.WebUI;
import com.haiyen.reports.AllureManager;
import com.haiyen.reports.ExtentReportManager;
import com.haiyen.reports.ExtentTestManager;
import com.haiyen.utils.LogUtils;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext result) {
        PropertiesHelper.loadAllFiles();
        LogUtils.info("Start Testing " + result.getName());
        System.out.println("onStart: " + result.getStartDate());
        CaptureHelper.startRecord(result.getName());
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("End Testing" + result.getName());
        ExtentReportManager.getExtentReports().flush(); // kết thúc và thực thi xuất report
        System.out.println("onFinish: " + result.getEndDate());
        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info(getTestName(result) + " test is starting...");
        ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            CaptureHelper.startRecord(result.getName());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LogUtils.error("Test failed but it is in defined success ratio " + getTestName(result));
        ExtentTestManager.logMessage("Test failed but it is in defined success ratio " + getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info(result.getName() + " is passed");
        ExtentTestManager.logMessage(Status.PASS, getTestName(result) + " is passed");

        if (PropertiesHelper.getValue("SCREENSHOT_PASS").equals("true")) {
            CaptureHelper.takeScreenshot(result.getName());
        }

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            WebUI.sleep(0.5);
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        CaptureHelper.takeScreenshot(result);
        LogUtils.error(result.getName() + " is failed");

        if (PropertiesHelper.getValue("SCREENSHOT_FAIL").equals("true")) {
            CaptureHelper.takeScreenshot(result.getName());
        }

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            WebUI.sleep(0.5);
            CaptureHelper.stopRecord();
        }

        //Add Screenshot to ExtentReport
        ExtentTestManager.addScreenShot(Status.FAIL, "Evidence " + getTestName(result) + " failed");
        //In log error khi lỗi vào report
        ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString());
        ExtentTestManager.logMessage(Status.FAIL, getTestName(result) + " is failed");

        //Allure report
        LogUtils.error("Screenshot captured for test case: " + getTestName(result));
        //AllureManager.saveTextLog(getTestName(result) + "is failed and screenshot taken!");
        AllureManager.saveScreenshotPNG();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Đây là test case bị bỏ qua: " + result.getName());
        ExtentTestManager.logMessage(Status.SKIP, getTestDescription(result) + " is skipped");

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            WebUI.sleep(0.5);
            CaptureHelper.stopRecord();
        }
    }
}
