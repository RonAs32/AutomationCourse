package Demo;

import Extensions.UserActions;
import Extensions.Verifications;
import Utilities.Operations;
import WorkFlows.WebFlows;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Utilities.Listeners.class)
public class FirstSuite extends Operations {
    @BeforeClass
    public void Login(){
        UserActions.SendKeys(loginPage.txt_username, dr.GetData("username"));
        UserActions.SendKeys(loginPage.txt_password, dr.GetData("password"));
        UserActions.Click(loginPage.btn_login);
        Verifications.VerifyElementIsPresent(mainPage.btn_appLauncher);
    }

    @AfterMethod
    public void AfterEveryTest(){
        if (isDriverInsideWindow){
            driver.switchTo().defaultContent();
            isDriverInsideWindow = false;
        }
    }

    @Test
    public void SearchForCasesObject(){
        WebFlows.SearchInAppLauncher("Cases");
    }

    @Test
    public void OpenUsersInSetup(){
        UserActions.SendKeys(setupPage.search_setupQuickFind, "Users");
        UserActions.Click(setupPage.link_Users);
        Verifications.VerifyElementIsPresent(driver.findElement(By.xpath("//span[text()='Users']")));
    }

    @Test
    public void OpenProfileSettings(){
        mainPage.btn_userProfile.click();
        mainPage.btn_profile_settings.click();
    }

    @Test
    public void ArrowDownInSetupSearch(){
        mainPage.txt_SetupMainSearch.click();
    }

    @Test
    public void OpenSetupTabAndSwitchTabs(){
        mainPage.btn_Setup.click();
        mainPage.btn_SetupOption.click();
        driver.switchTo().window(driver.getWindowHandle());
        isDriverInsideWindow = true;
    }

}
