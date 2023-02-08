package Extensions;

import Utilities.Operations;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class RestActions extends Operations {

    public static String GetValueFromSpecificRecordCell(String object, String whereField, String whereValue  ,String column){
        try {
            String query = "/query?q=Select+"+column+"+From+"+object+"+where+"+whereField+"="+"'"+whereValue+"'";
            System.out.println(query);
            //Set up the HTTP objects needed to make the request.
            httpClient = HttpClientBuilder.create().build();

            String uri = baseUri + query;
            System.out.println("Base Uri" + baseUri);

            httpGet = new HttpGet(uri);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);

            // Make the request.
            httpResponse = httpClient.execute(httpGet);
            // Process the result
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                response_string = EntityUtils.toString(httpResponse.getEntity());
                JSONObject json = new JSONObject(response_string);
                JSONArray j = json.getJSONArray("records");
                System.out.println(j.getJSONObject(0));
                System.out.println("The value is : "+j.getJSONObject(0).getString(column));
                if (j.getJSONObject(0).isNull(column)){
                    return "";
                }else{
                    return j.getJSONObject(0).getString(column);
                }
            } else{
                System.exit(-1);
            }
        }
        catch (IOException ioe) {
            System.out.println("could not connect");
            ioe.printStackTrace();
        }
        return null;
    }

    public static void DeleteSingleRecordFromObject(String object, String recordID) {
        //Notice, the id for the record to update is part of the URI, not part of the JSON
        String uri = baseUri + "/sobjects/" +object+ "/" + recordID;
        System.out.println(uri);
        try {
            //Set up the objects necessary to make the request.
            httpClient = HttpClientBuilder.create().build();

            httpDelete = new HttpDelete(uri);
            httpDelete.addHeader(oauthHeader);
            httpDelete.addHeader(prettyPrintHeader);

            //Make the request
            httpResponse = httpClient.execute(httpDelete);

            //Process the response
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Deleted the record successfully.");
            } else {
                System.out.println("record delete NOT successful. Status code is " + statusCode);
                response_string = EntityUtils.toString(httpResponse.getEntity());
                JSONArray json = new JSONArray(response_string);
                json.getJSONArray(0).getString(0);
                System.out.println(json);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public static void CreateRecordFromJson(JSONObject jsonObject, String targetedObjectPath) {
        String uri = baseUri + targetedObjectPath;
        System.out.println(uri);
        try {
            //Construct the objects needed for the request
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(uri);
            httpPost.addHeader(oauthHeader);
            httpPost.addHeader(prettyPrintHeader);
            // The message we are going to post
            StringEntity body = new StringEntity(jsonObject.toString());
            System.out.println("Json " + jsonObject);
            body.setContentType("application/json");
            httpPost.setEntity(body);

            //Make the request
            httpResponse = httpClient.execute(httpPost);

            //Process the results
            statusCode = httpResponse.getStatusLine().getStatusCode();
            response_string = EntityUtils.toString(httpResponse.getEntity() );
            if (statusCode == 201 || statusCode == 200) {
                JSONObject json = new JSONObject(response_string);
                createdRecordID = json.getString("id");
                currentTest.info("Created record from request - ID : " + createdRecordID);
                System.out.println("Successful response with code : " + statusCode);
            } else {
                System.out.println(" Error. Status code " + statusCode);
                currentTest.fail(response_string);
            }
            System.out.println("Response: " + response_string);
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException | NullPointerException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void UpdateRecordFromJson(JSONObject jsonObject, String targetedObjectPath, String targetId) {
        String uri = baseUri + targetedObjectPath + "/" + targetId;
        System.out.println(uri);
        try {
            //Construct the objects needed for the request
            httpClient = HttpClientBuilder.create().build();
            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.addHeader(oauthHeader);
            httpPatch.addHeader(prettyPrintHeader);
            // The message we are going to post
            StringEntity body = new StringEntity(jsonObject.toString());
            System.out.println("Json " + jsonObject);
            body.setContentType("application/json");
            httpPatch.setEntity(body);

            //Make the request
            httpResponse = httpClient.execute(httpPatch);

            //Process the results
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 201 || statusCode == 200 || statusCode == 204) {
                System.out.println("Successful response with code : " + statusCode);
                currentTest.pass("Update request " + response_string + " is successful");
            } else {
                System.out.println(" Error. Status code " + statusCode);
                currentTest.fail(response_string);
            }
            System.out.println("Response: " + response_string);
        } catch (JSONException e) {
            System.out.println("Issue updating JSON or processing results");
            e.printStackTrace();
        } catch (IOException | NullPointerException ioe) {
            ioe.printStackTrace();
        }
    }

    private static class HttpPatch extends HttpPost {
        public HttpPatch(String uri) {
            super(uri);
        }

        public String getMethod() {
            return "PATCH";
        }
    }
}
