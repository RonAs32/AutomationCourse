package Utilities;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Listeners extends Operations implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("------------------ Starting test: " + iTestResult.getName() + "------------------");
        currentTest = extent.createTest("Test Name : "+iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("------------------ Starting succeed: " + iTestResult.getName() + "------------------");

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("------------------ Starting failed: " + iTestResult.getName() + "------------------");
        try {
            if (iTestResult.getStatus() == ITestResult.FAILURE && !isApiTest) {
                takescreen(iTestResult);
            }
            System.out.println("test failed, taking image");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("this is on start");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("this is on end");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public String takescreen(ITestResult result) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        File imageFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        File localScreenshots = new File(new File("target"), "screenshots");

        if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
            localScreenshots.mkdirs();
        }

        File screenshot = new File(localScreenshots, result.getName()+timeStamp+ ".png");
        FileUtils.moveFile(imageFile, screenshot);

        System.out.println(screenshot.getAbsolutePath());

        String extentScrnshot =screenshot.getAbsolutePath();

        return extentScrnshot;
    }
}
