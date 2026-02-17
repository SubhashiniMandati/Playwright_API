package tests.GET;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.HttpHeader;
import reports.ExtentTestManager;
import tests.base.BaseApiTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class APIResponseHeadersTest extends BaseApiTest {

    @Test(groups = {"smoke"})
    public void getHeadersTest(){
        APIResponse apiResponse = client.get(ConfigReader.getProperty("users"));
        int statusCode = apiResponse.status();
        Assert.assertEquals(statusCode, 200);
        //using map:
       Map<String, String> headersMap =  apiResponse.headers();
        headersMap.forEach((k,v) -> System.out.println(k + ":" + v));
        ExtentTestManager.getTest().info("total response headers: "+headersMap.size());
        Assert.assertEquals(headersMap.get("server"), "cloudflare");
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        System.out.println("===============================");
        //using list:
       List<HttpHeader> headersList = apiResponse.headersArray();
        for(HttpHeader e : headersList){
            System.out.println(e.name + " : " + e.value);
            ExtentTestManager.getTest().info(e.name +"  :  "+headersMap.size());
        }
    }
}
