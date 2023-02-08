package Extensions;

import Utilities.Operations;
import org.testng.Assert;

public class RestVerifications extends Operations {

    public static void VerifySuccessResponse(){
        try{
            Assert.assertTrue(statusCode == 200 || statusCode == 201 || statusCode == 204);
            currentTest.pass("Request success");
        } catch (Exception e){
            Assert.fail();
            currentTest.fail("Request failed");
        }
    }

    public static void VerifyStringResponseEqual(String expected, String actual){
        try {
            Assert.assertEquals(expected, actual);
            currentTest.pass("Value : " + expected +" is equal to " + actual);
        } catch (Exception e){
            Assert.fail();
            currentTest.fail("Value :" + expected + "is not equal to " + actual);
        }
    }
}
