package chat.automation.framework.core;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.IInvokedMethod;
import com.aventstack.extentreports.ExtentReports;
import chat.automation.framework.utils.Logger;
import chat.automation.framework.utils.Reporter;
import io.cucumber.java.Scenario;

public class FrameworkListener implements ITestListener, IExecutionListener {
	//private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private long startTime, endTime;
	private static ExtentReports report;
	public static Scenario scenario;
	
	
	@Override
	public void onStart(ITestContext context) {
		System.out.println("onStart Listner");
		Logger.info("<#################### S-T-R-A-T #####################################");
		Logger.info("[" + context.getName() + "]");
		 InetAddress localhost = null;
			try {
				localhost = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	         String hostname = localhost.getHostName();
	         System.setProperty("Environment", hostname);
	         Reporter.getReport();
	}

	  public void afterInvocation(IInvokedMethod invocationMethod, ITestResult testResult) {
		System.out.println("afterInvocation-List");
	  }
	@Override
	public void onTestStart(ITestResult testResult) {
		System.out.println("onTestStart-List");
		
	        //String featureName = FrameworkHooks.FeatureName;
	       // System.out.println("On Test Start Starting feature: " + featureName);
	        //System.out.println("On Test Start Starting scenario: " + scenarioName);
		//String ScenarioName=System.getProperty("ScenarioName");
		//dummyMethod(scenario);
		//String ScenarioName=Reporter.getScenarioName();
		//System.out.println(ScenarioName +"in On Test Start");
		//Reporter.startTest(ScenarioName);
//		String scenarioName = Reporter.getScenarioName();
//		System.out.println("OnTestStart<" + scenarioName + ">");
//		Logger.info("<" + scenarioName + ">");
		
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		//test.get().pass("Test passed");
		String scenarioName = Reporter.getScenarioName();
		System.out.println("OnTestSuccess</" + scenarioName + ">");
		Logger.info("</" + scenarioName + ">");
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		//test.get().fail(testResult.getThrowable());
		String scenarioName = Reporter.getScenarioName();
		System.out.println("OnTestFailure</" + scenarioName + ">");
		Logger.info("</" + scenarioName + ">");
		
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		//test.get().skip("Test skipped");
		String scenarioName = Reporter.getScenarioName();
		Logger.info("</" + scenarioName + ">");
		System.out.println("onTestSkipped</" + scenarioName + ">");
	}

	@Override
	public void onExecutionStart() {
		startTime = System.currentTimeMillis();
		System.out.println("StartTime:"+((System.currentTimeMillis()/1000)/60));
	}

	@Override
	public void onExecutionFinish() {
		endTime = ((System.currentTimeMillis() - startTime) / 1000) / 60;
		System.out.println("endTime:"+endTime);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("OnFinish"+ Reporter.getScenarioName());
		report=Reporter.getReport();
		report.flush();
		//Reporter.getTest(Reporter.getScenarioName());
		Logger.info("[/" + context.getName() + "]");
		Logger.info("<#################### E---N---D" + endTime + "########################");
	}

	
}
