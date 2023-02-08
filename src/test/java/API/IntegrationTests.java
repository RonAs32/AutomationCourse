package API;

import Extensions.RestActions;
import Extensions.RestVerifications;
import Utilities.JsonPayloads;
import Utilities.Operations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Utilities.Listeners.class)
public class IntegrationTests extends Operations {
    @AfterMethod
    public void AfterEveryTest(){
        statusCode = 0;
    }

    @Test
    public void testAPIConnection (){
        RestActions.DeleteSingleRecordFromObject("Account", "0018d00000OeiMKAAZ");
    }

    @Test
    public void CreateSampleCase(){
        RestActions.CreateRecordFromJson(JsonPayloads.CreateCaseJson(), JsonPayloads.path_Case);
    }

    @Test
    public void UpdateSampleCase(){
        RestActions.UpdateRecordFromJson(JsonPayloads.UpdateCaseJson(), JsonPayloads.path_Case, "5008d00000K2H0LAAV");
    }

    @Test
    public void CreateAndUpdateCase(){
        RestActions.CreateRecordFromJson(JsonPayloads.CreateCaseJson(), JsonPayloads.path_Case);
        RestActions.UpdateRecordFromJson(JsonPayloads.UpdateCaseJson(), JsonPayloads.path_Case, createdRecordID);
        RestVerifications.VerifyStringResponseEqual("Email",
                RestActions.GetValueFromSpecificRecordCell("Case", "id", "5008d00000K2H0LAAV", "Origin"));
    }

}
