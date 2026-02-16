package com.qa.api.tests.PUT;

import client.ApiClient;
import com.api.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.qa.api.tests.base.BaseApiTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;

public class UpdateUserPUTCallWithPOJOLombokTest extends BaseApiTest {
    ApiClient client;
    @BeforeMethod
    public void initClient() {
        client = new ApiClient(requestContext);
    }

    //1. post - user id = 123
    //2. put - user id - /123
    //3. get -- user id /123

    static String emailId;
    public static String getRandomEmail(){
        emailId = "testpwautomation"+ System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    @Test
    public void updateUserTest() throws IOException {
        //create users object: using builder pattern:
        User user = User.builder()
                .name("Subhashini")
                .email(getRandomEmail())
                .gender("female")
                .status("active").build();

        //1. POST Call: create a user
        APIResponse apiPostResponse = client.post(ConfigReader.getProperty("users"),user);
        System.out.println(apiPostResponse.url());
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        String responseText = apiPostResponse.text();
        System.out.println(responseText);

        //convert response text/json to POJO -- desrialization
        ObjectMapper objectMapper = new ObjectMapper();
        User actUser = objectMapper.readValue(responseText, User.class);
        System.out.println("actual user from the response---->");
        System.out.println(actUser);

        Assert.assertEquals(actUser.getName(), user.getName());
        Assert.assertEquals(actUser.getEmail(), user.getEmail());
        Assert.assertEquals(actUser.getStatus(), user.getStatus());
        Assert.assertEquals(actUser.getGender(), user.getGender());
        Assert.assertNotNull(actUser.getId());
        String userId = actUser.getId();
        System.out.println("new user id is : " + userId);

        //update status active to inactive
        user.setStatus("inactive");
        user.setName("Subhashini Playwright");
        System.out.println("---------------PUT CALL----------------");

        //2. PUT Call - update user:
        APIResponse apiPUTResponse = client.put(ConfigReader.getProperty("users")+"/" + userId,user);
        System.out.println(apiPUTResponse.status() + " : " + apiPUTResponse.statusText());
        Assert.assertEquals(apiPUTResponse.status(), 200);
        String putResponseText = apiPUTResponse.text();
        System.out.println("update user : " + putResponseText);
        User actPutUser = objectMapper.readValue(putResponseText, User.class);
        Assert.assertEquals(actPutUser.getId(), userId);
        Assert.assertEquals(actPutUser.getStatus(), user.getStatus());
        Assert.assertEquals(actPutUser.getName(), user.getName());
        System.out.println("---------------GET CALL----------------");

        //3. Get the updates user with GET CALL:
        APIResponse apiGETResponse = client.get(ConfigReader.getProperty("users")+"/"+userId);
        System.out.println(apiGETResponse.url());
        int statusCode = apiGETResponse.status();
        System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiGETResponse.ok(), true);
        String statusGETStatusText = apiGETResponse.statusText();
        System.out.println(statusGETStatusText);
        String getResponseText = apiGETResponse.text();
        User actGETUser = objectMapper.readValue(getResponseText, User.class);
        Assert.assertEquals(actGETUser.getId(), userId);
        Assert.assertEquals(actGETUser.getStatus(), user.getStatus());
        Assert.assertEquals(actGETUser.getName(), user.getName());

    }
}
