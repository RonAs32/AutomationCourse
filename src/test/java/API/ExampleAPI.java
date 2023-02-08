package API;

import Utilities.Operations;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

@Listeners(Utilities.Listeners.class)
public class ExampleAPI extends Operations {

    @Test
    public void CreateSimpleUser() {
        RestAssured.baseURI = dr.GetData("urlAPI");
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
    /*I have put a unique username and password as below,
    you can enter any as per your liking. */
        requestParams.put("name", "ron");
        requestParams.put("job", "admin");

        request.body(requestParams.toString());
        Response response = request.post("/api/users");

        Assert.assertEquals(response.getStatusCode(), 201);
        System.out.println(response.getBody().asString());
        System.out.println("User Created!");
    }
}
