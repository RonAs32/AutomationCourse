package Utilities;

import com.google.gson.JsonObject;
import org.json.JSONObject;

public class JsonPayloads extends Operations {
    public static String path_Case = "/sobjects/Case";

    public static JSONObject CreateCaseJson(){
        JSONObject createCase = new JSONObject();
        createCase.put("Origin", "Phone");
        return createCase;
    }

    public static JSONObject UpdateCaseJson(){
        JSONObject createCase = new JSONObject();
        createCase.put("Origin", "Email");
        return createCase;
    }



}
