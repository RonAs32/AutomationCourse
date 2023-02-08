package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Base {

    //drivers
    public static WebDriver driver;
    public static WebDriverManager driverManager;

    //extras
    public static Actions actions;
    protected static WebDriverWait wait;

    //Extent Report
    protected static ExtentReports extent = new ExtentReports();
    protected static ExtentSparkReporter htmlReporter;
    protected static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
    protected static LocalDate localDate = LocalDate.now();

    //Every page in the web used defined once
    protected static Pages.LoginPage loginPage;
    protected static Pages.MainPage mainPage;
    protected static Pages.SetupPage setupPage;

}
