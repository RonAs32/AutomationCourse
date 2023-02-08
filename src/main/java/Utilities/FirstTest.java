package Utilities;

import Extensions.UserActions;
import Extensions.Verifications;
import Pages.MainPage;
import WorkFlows.WebFlows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Utilities.Listeners.class)
public class FirstTest extends Operations{

    @Test
    void SearchLaptopTest(){
        WebFlows.SearchInAppLauncher("Laptop");
        Verifications.VerifyElementIsPresent(mainPage.btn_appLauncher);
    }
}
