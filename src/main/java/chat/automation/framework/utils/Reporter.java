package chat.automation.framework.utils;

import java.io.File;
//import java.security.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import chat.automation.framework.helper.ActionsHelper;

import com.aventstack.extentreports.Status;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.GherkinKeyword;

public class Reporter{
	
	public static ExtentReports report;
	public static ThreadLocal<String> scenarioName = new ThreadLocal<>();
	public static ThreadLocal<String> featureName = new ThreadLocal<>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	public static ExtentTest test;
	public static ExtentTest node;
	public static Map<String, ExtentTest> scenarioMap = new HashMap<>();
	
	
	public synchronized static ExtentReports getReport() {
		try {
			if (report == null) {
				String myReportPath = new Timestamp(new Date().getTime()).toString().replace(" ", "").replace(":", "_").replace(".","_");
				ExtentSparkReporter spark= new ExtentSparkReporter(FrameworkConstants.PROJECT_PATH +"/Reports/myReport_"+ myReportPath + ".html");
				report= new ExtentReports();
				report.setSystemInfo("developed_By", System.getProperty("user.name"));
				report.setSystemInfo("orgName", FrameworkConstants.OrgName);
				report.setSystemInfo("Machine_Name",System.getProperty("Environment"));
			 	report.attachReporter(spark);
			}		
		} catch (Exception exception) {
			return null;
		}
		return report;	
	}


	public static void SetScenario(Scenario scenario) {
		System.out.println("In SetScenario"+scenario.getName());
		scenarioName.set(scenario.getName());
		featureName.set(scenario.getUri().toString().split("/")[scenario.getUri().toString().split("/").length - 1]);
		
	}
	
	public static String getScenarioName() {
        return scenarioName.get();
    }
	public static String getFeatureName() {
        return featureName.get();
    }
	
	public synchronized static ExtentTest getTest(String scenarioName) {
		//test=report.g
		return getTest(scenarioName);
	}
	public synchronized static void startTest(String scriptName){
		System.out.println("In startTest");
		test= report.createTest(getScenarioName());
		scenarioMap.put(scriptName, test);
		test.log(Status.INFO, "Started Test Case"+scriptName);
		//test.log(Status.INFO, "[" +scriptName +"]");
		
		//(Status.INFO,"[" +scriptName +"]","[" +scriptName + "]" );
	}
	public synchronized static void startNode(String GherkinKeyword, String StepDetails) throws ClassNotFoundException{
		System.out.println("In start Node");
		node= test.createNode(new GherkinKeyword(GherkinKeyword), StepDetails);
		node.log(Status.INFO, "Started Node:[" +GherkinKeyword + "]"+StepDetails );
		System.out.println("Started Node <"+GherkinKeyword +">"+ StepDetails);
		//return node;
		//test.log(Status.INFO, "[" +scriptName +"]");
		
		//(Status.INFO,"[" +scriptName +"]","[" +scriptName + "]" );
	}
//	
	public synchronized static void endTest(String scriptName){
		ExtentTest test = null;
		test = getTest(scriptName);
		test.log(Status.INFO, "Ended Test Case Execution"+scriptName);
		//if (test != null) {
			//getReport().removeTest(scriptName);
			//getReport().flush();
		//}
	
	}
	
	public synchronized static void ReportEvent(Status status,String stepName, String stepDescription) {
		String screenShotPath = null;
		String newStepName = stepName.replace("<", "").replace(">", "");
		if(node != null) {
			
		
			if((status.equals(Status.FAIL))){
				if(!(Environment.getProperty("BrowserType").trim().toUpperCase().equals("API"))){
					screenShotPath = getScreenshot();
					System.out.println(screenShotPath);
					node.log(status,"["+newStepName +"]"+",  "+"["+stepDescription+"]" + node.addScreenCaptureFromPath(screenShotPath));
				}else {
					node.log(status,"["+newStepName +"]"+",  "+"["+stepDescription+"]");
				}
				
				
			}
			else{
				node.log(status,"["+newStepName +"]"+",  "+"["+ stepDescription +"]");
			}
		}else {
			if((status.equals(Status.FAIL))){
				screenShotPath = getScreenshot();
				if (screenShotPath != null) {
					System.out.println("ScreenshotPath"+screenShotPath);
					test.log(status,"["+newStepName +"]"+",  "+"["+stepDescription+"]"+ node.addScreenCaptureFromPath(screenShotPath));
				}else {
					test.log(status,"["+newStepName +"]"+",  "+"["+stepDescription+"]"+ node.addScreenCaptureFromPath("c:\\"));
				}
				
				
			}
			else{
				test.log(status,"["+newStepName +"]" +",  "+"["+stepDescription +"]");
			}
			
		}
	}
	
	private synchronized static String getScreenshot() {
		File srcFile,destFile = null;
		try{
			WebDriver webDriver = DriverFactory.getWebDriver();
			srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			destFile = new File(FrameworkConstants.screenShotsPath + new Timestamp(new Date().getTime()).toString().replace(" ", "").replace(":", "_").replace(".","_") +".jpg");
			FileUtils.copyFile(srcFile, destFile);
		}catch(Exception exception){
			return null;	
		}
		return destFile.getAbsolutePath();
	}
	
	
	public synchronized static void exitTest(){
		test.log(Status.FAIL,"[Reporter:exitTest]"+"[Reporter:exitTest]" );
		Reporter.ReportEvent(Status.FAIL, "ExitTest", "ExitTest");
		report.flush();
		ActionsHelper.OnTerminate();
		Thread.currentThread().interrupt();
	}
	
}
