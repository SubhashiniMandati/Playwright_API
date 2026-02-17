package tests.DELETE;

import com.api.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import reports.ExtentTestManager;
import tests.base.BaseApiTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Optional;

public class DeleteUserAPITest extends BaseApiTest {
    //1. create a user -- user id -- 201
    //2. delete user -- user id -- 204
    //3. get user -- user id -- 404

    static String emailId;

    public static String getRandomEmail(){
        emailId = "testpwautomation"+ System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    @Test(groups = "smoke")
    public void deleteUserTest() throws IOException {

        //1. create users object: using builder pattern:
        User users = User.builder()
                .name("Subhashini")
                .email(getRandomEmail())
                .gender("female")
                .status("active").build();

        //POST Call: create a user
        APIResponse apiPostResponse = getClient().post(ConfigReader.getProperty("users"),users);
        ExtentTestManager.getTest().info("URL "+apiPostResponse.url());
        ExtentTestManager.getTest().info(String.valueOf("Status "+apiPostResponse.status()));
        Assert.assertEquals(apiPostResponse.status(), 201);
        String responseText = apiPostResponse.text();
        ExtentTestManager.getTest().info(String.valueOf("responseText: "+responseText));
        //convert response text/json to POJO -- desrialization
        ObjectMapper objectMapper = new ObjectMapper();
        User actUser = objectMapper.readValue(responseText, User.class);
        System.out.println("actual user from the response---->");
        System.out.println(actUser);
        Assert.assertNotNull(actUser.getId());
        String userId = actUser.getId();
        ExtentTestManager.getTest().info(userId);

        //2. delete user -- user id -- 204
        APIResponse apiDELETEResponse = getClient().delete(ConfigReader.getProperty("users")+"/"+userId);
        ExtentTestManager.getTest().info("apiDELETEResponse "+String.valueOf(apiDELETEResponse.status()));
        ExtentTestManager.getTest().info("statusText "+apiDELETEResponse.statusText());
        Assert.assertEquals(apiDELETEResponse.status(), 204);
        System.out.println("delete user response body ====" + apiDELETEResponse.text());

        //3. get user -- user id -- 404
        APIResponse apiResponse = getClient().get(ConfigReader.getProperty("users")+"/"+userId);
        System.out.println(apiResponse.text());
        int statusCode = apiResponse.status();
        ExtentTestManager.getTest().info("GetstatusCode "+String.valueOf(statusCode));
        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals(apiResponse.statusText(), "Not Found");
        Assert.assertTrue(apiResponse.text().contains("Resource not found"));
    }
}
