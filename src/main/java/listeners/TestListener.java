package listeners;

import com.aventstack.extentreports.*;
import org.testng.*;
import reports.ExtentManager;
import reports.ExtentTestManager;


public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {

//        ExtentTest test01 = extent.createTest(result.getMethod().getMethodName());
//        ExtentTestManager.setTest(test01);
        if(ExtentTestManager.getTest()==null){
            ExtentTest test =
                    ExtentManager.getInstance().createTest(result.getMethod().getMethodName());

            ExtentTestManager.setTest(test);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
