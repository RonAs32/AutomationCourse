package Extensions;

import Utilities.Operations;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserActions extends Operations {

    public static void Click(WebElement element){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            currentTest.info("Clicked on element : " + element);
        } catch (Exception e){
            currentTest.fail("Test failed " + e);
        }
    }

    public static void SendKeys(WebElement element, String text){
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        currentTest.info("Entered : " + text + " inside : " + element);
    }
}
