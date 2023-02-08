package Extensions;

import Utilities.Operations;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Verifications extends Operations {

    public static void VerifyElementIsPresent(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed());
        currentTest.pass(element.getText() + " is Displayed");
    }

    public static void VerifyElementIsNotPresent(WebElement element){
        Assert.assertFalse(element.isDisplayed());
    }

    public static void VerifyElementsAreEqual(WebElement element, String expected){
        Assert.assertEquals(element.getText(), expected);
    }


}
