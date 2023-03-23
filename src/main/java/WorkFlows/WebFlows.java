package WorkFlows;

import Extensions.UserActions;
import Utilities.Operations;
import org.openqa.selenium.By;

public class WebFlows extends Operations {

    public static void SearchInAppLauncher(String value){
        UserActions.Click(mainPage.btn_appLauncher);
        UserActions.SendKeys(mainPage.txt_appLauncher, value);
        UserActions.Click(driver.findElement(By.xpath("//a[@data-label='"+value+"']/div/lightning-formatted-rich-text/span/p")));
  //      UserActions.Click(mainPage.link_appLauncherResult);
    }

    public static void OpenSetupWindow(){
        UserActions.Click(mainPage.btn_Setup);
        UserActions.Click(mainPage.btn_SetupOption);
    }

}
