package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Operations extends Base {

    public static boolean isDriverInsideWindow = false;

    public String environment;
    public static DataReader dr = new DataReader();
    public static ExtentTest currentTest = extent.createTest("Test");
    public static boolean isApiTest = false;

    //RESTAPI Variables
    public static String REST_USERNAME, REST_PASSWORD, REST_URL, GRANTSERVICE, CLIENTID, CLIENTSECRET, REST_ENDPOINT, API_VERSION;
    public static String baseUri;
    public static String baseLoginUrl;
    public static Header oauthHeader;
    public static HttpPost httpPost;
    public static HttpPatch httpPatch;
    public static HttpDelete httpDelete;
    public static HttpClient httpClient;
    public static HttpResponse httpResponse;
    public static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
    public static HttpGet httpGet;
    public static String response_string;
    public static int statusCode;

    public static String createdRecordID;


    public static WebDriver InitiateChromeDriver(){
        WebDriverManager.chromiumdriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public void InitiateBrowser(){
        driver = InitiateChromeDriver();
        driver.manage().window().maximize();
        driver.get(dr.GetData("url"));
        PageManager.InitiatePages();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, 10);
        //generate report
        htmlReporter = new ExtentSparkReporter("ExtentReports/extentReport" + dtf.format(localDate) +".html");
        extent.attachReporter(htmlReporter);
    }

    public void InitiateAPIVariables(){
        //Setup API Report
        htmlReporter = new ExtentSparkReporter("ExtentReports/extentReport" + dtf.format(localDate) +"Integration.html");
        htmlReporter.config().setDocumentTitle("Text-Automation");
        htmlReporter.config().setReportName("Regression");
        htmlReporter.config().setTheme(Theme.DARK);
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environment","TEST");
        extent.setReportUsesManualConfiguration(true);
        extent.attachReporter(htmlReporter);

        //these credentials are same for all env.
        REST_URL = dr.GetData("RestUrl");
        GRANTSERVICE = dr.GetData("GrantService");
        REST_ENDPOINT = dr.GetData("Rest_EndPoint");
        API_VERSION = dr.GetData("API_Version");

        //credentials specific for each environment
        REST_USERNAME = dr.GetData("RestUserName");
        REST_PASSWORD = dr.GetData("RestPassword");
        CLIENTID = dr.GetData("ClientID");
        CLIENTSECRET = dr.GetData("ClientSecret");

    }

    public void InitiateAPIConnection(){
        //Before Class method runs one time before every execution (not before every test)
        InitiateAPIVariables(); //Method in commonOps to initiate all variables of the target environment
        httpClient = HttpClientBuilder.create().build();
        String loginURL = REST_URL +
                GRANTSERVICE +
                "&client_id=" + CLIENTID +
                "&client_secret=" + CLIENTSECRET +
                "&username=" + REST_USERNAME +
                "&password=" + REST_PASSWORD;
        System.out.println("login url :" + loginURL);

        // Login requests must be POSTs
        httpPost = new HttpPost(loginURL);
        httpResponse = null;

        // Execute the login POST request
        try {
            httpResponse = httpClient.execute(httpPost);
            System.out.println("response : " + httpResponse);
        } catch (IOException cpException) {
            cpException.printStackTrace();
        }

        // verify response is HTTP OK - must be 200 otherwise will fail to connect.
        final int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
        }

        //store the response received in a getResult string
        String getResult = null;
        try {
            getResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //define JSON Object and the access token that will be used to fetch the retrieved information.
        JSONObject jsonObject;
        String loginAccessToken = null;
        String loginInstanceUrl = null;

        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        baseLoginUrl = loginInstanceUrl;
        baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION ;
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken) ;
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + httpResponse.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        System.out.println("baseUri: "+ baseUri);
    }

    @BeforeClass
    public void ExecuteBeforeClass(){
        environment = "api";
        switch(environment) {
            case "api":
                isApiTest = true;
                System.out.println("this is api test");
                InitiateAPIConnection();
                break;
            case "ui":
                isApiTest = false;
                System.out.println("this is ui test");
                InitiateBrowser();
                break;
        }
    }

    @AfterClass
    public void ExecuteAfterClass(){
        extent.flush();
    }
}
