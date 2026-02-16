package com.qa.api.tests.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import config.ConfigReader;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.Map;

public class BaseApiTest {
    protected  Playwright playwright;
    protected  APIRequest request;
    protected  APIRequestContext requestContext;

    static String emailId;

//    @BeforeTest
//    public void setup(){
//        playwright = Playwright.create();
//        request =  playwright.request();
//        requestContext = request.newContext();
//    }
    @BeforeTest
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
    }


    @AfterTest
    public void tearDown(){
        requestContext.dispose();
        playwright.close();
    }
}
