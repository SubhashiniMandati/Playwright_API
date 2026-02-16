package com.qa.api.tests.POST;

import client.ApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.qa.api.tests.base.BaseApiTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateUserPostCallTest extends BaseApiTest {
    ApiClient client;
    static String emailId;
    public static String getRandomEmail(){
        emailId = "testpwautomation"+ System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }
    @BeforeMethod
    public void initClient() {
        client = new ApiClient(requestContext);
    }

    @Test
    public void createUserTest() throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "Subhashini");
        data.put("email", getRandomEmail());
        data.put("gender", "Female");
        data.put("status", "active");

        //POST Call: create a user
        APIResponse apiPostResponse = client.post(ConfigReader.getProperty("users"),data);
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        System.out.println(apiPostResponse.text());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());

            //capture id from the post json response:
        String userId = postJsonResponse.get("id").asText();
        System.out.println("user id : " + userId);

        //GET Call: Fetch the same user by id:
        System.out.println("===============get call response============");
        APIResponse apiGetResponse = client.get(ConfigReader.getProperty("users")+"/"+ userId);
            Assert.assertEquals(apiGetResponse.status(), 200);
            Assert.assertEquals(apiGetResponse.statusText(), "OK");
            System.out.println(apiGetResponse.text());
            Assert.assertTrue(apiGetResponse.text().contains(userId));
            Assert.assertTrue(apiGetResponse.text().contains("Subhashini"));
            Assert.assertTrue(apiGetResponse.text().contains(emailId));
    }
}
