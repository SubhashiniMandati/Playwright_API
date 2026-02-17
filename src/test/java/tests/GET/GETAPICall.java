package tests.GET;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import tests.base.BaseApiTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GETAPICall extends BaseApiTest {

    @Test
    public void getSpecificUserApiTest() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("gender", "male");
        params.put("status", "active");
        APIResponse apiResponse = client.get(ConfigReader.getProperty("users"), params);
        int statusCode = apiResponse.status();
        System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);
        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);
        System.out.println("----print api response with plain text----");
        System.out.println(apiResponse.text());
        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyRespose = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyRespose);

    }

    @Test
    public void getUsersApiTest() throws IOException {
        APIResponse apiResponse = client.get(ConfigReader.getProperty("users"));
       int statusCode = apiResponse.status();
       System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);
        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);
        System.out.println("----print api response with plain text----");
        System.out.println(apiResponse.text());
        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
       JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
       String jsonPrettyRespose = jsonResponse.toPrettyString();
       System.out.println(jsonPrettyRespose);
        System.out.println("----print api url----");
        System.out.println(apiResponse.url());
        System.out.println("----print response headers----");
       Map<String, String> headersMap = apiResponse.headers();
        System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals(headersMap.get("x-download-options"), "noopen");
    }
}
