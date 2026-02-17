package tests.base;

import client.ApiClient;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import config.ConfigReader;
import org.testng.annotations.*;

import java.util.Map;
@Listeners(listeners.TestListener.class)
public class BaseApiTest {
    protected  Playwright playwright;
    protected  APIRequestContext requestContext;
    protected ApiClient client;

    @BeforeClass(alwaysRun = true)
    public void setupNew() {
        playwright = Playwright.create();


        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(ConfigReader.getProperty("baseUrl"))
                        .setExtraHTTPHeaders(Map.of(
                                "Content-Type", "application/json",
                                "Authorization",  ConfigReader.getProperty("token")
                        ))
        );
        client = new ApiClient(requestContext);
    }

    @AfterClass
    public void tearDown(){
        requestContext.dispose();
        playwright.close();
    }
}
