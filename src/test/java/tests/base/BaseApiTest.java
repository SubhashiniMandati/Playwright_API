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
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<APIRequestContext> requestContext = new ThreadLocal<>();
    private static ThreadLocal<ApiClient> client = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    public void setupNew() {
        playwright.set(Playwright.create());


        requestContext.set(
                playwright.get().request().newContext(
                        new APIRequest.NewContextOptions()
                                .setBaseURL(ConfigReader.getProperty("baseUrl"))
                                .setExtraHTTPHeaders(Map.of(
                                        "Content-Type", "application/json",
                                        "Authorization", ConfigReader.getProperty("token")
                                ))
                )
        );

        client.set(new ApiClient(requestContext.get()));
    }

    @AfterClass
    public void tearDown(){
        if (requestContext.get() != null) {
            requestContext.get().dispose();
            requestContext.remove();
        }

        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }

        client.remove();
    }
    // ===== Getters for child tests =====

    protected Playwright getPlaywright() {
        return playwright.get();
    }

    protected APIRequestContext getRequestContext() {
        return requestContext.get();
    }

    protected ApiClient getClient() {
        return client.get();
    }
}
