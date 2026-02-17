package tests.POST;

import com.api.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import tests.base.BaseApiTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class CreateUserPostCallWithPojoLombokTest extends BaseApiTest {
    static String emailId;
    public static String getRandomEmail(){
        emailId = "testpwautomation"+ System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    @Test
    public void createUserTest() throws IOException {
        //create users object: using builder pattern:
        User user = User.builder()
                    .name("Subhashini")
                    .email(getRandomEmail())
                    .gender("female")
                    .status("active").build();

        //POST Call: create a user
        APIResponse apiPostResponse = getClient().post(ConfigReader.getProperty("users"),user);
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
    }
}
