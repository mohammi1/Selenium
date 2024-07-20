package chat.automation.framework.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class ExpectedConditions {
		
		public static void assertEquals(boolean actualValue, boolean expectedValue) {
			try {
				Logger.info(getClassMethodName());
				Assert.assertEquals(actualValue, expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(),"Actual value '" + actualValue + "' is same as Expected Value '" + expectedValue + "'");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"Actual value '" + actualValue + "' is not same as Expected Value '" + expectedValue + "'");
			}
		}
		
		public static void assertTrue(boolean expectedValue){
			try {
				Logger.info(getClassMethodName());
				Assert.assertTrue(expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value 'true' is same as Expected 'true' Value");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), error.getMessage());
			}
		}
		
		public static void assertFalse(boolean expectedValue){
			try {
				Logger.info(getClassMethodName());
				Assert.assertFalse(expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value 'false' is same as Expected 'false' Value");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), error.getMessage());
			}
		}
		
		
		public static void assertEquals(int actualValue, int expectedValue){
			try {
				Logger.info(getClassMethodName());
				Assert.assertEquals(actualValue,expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value '" + actualValue + "' is same as Expected Value '" + expectedValue + "'");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), error.getMessage());
			}
		}

		public static void assertEquals(double actualValue, double expectedValue){
			try {
				Logger.info(getClassMethodName());
				Assert.assertEquals(actualValue,expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value '" + actualValue + "' is same as Expected Value '" + expectedValue + "'");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), error.getMessage());
			}
		}

		public static void assertEquals(String actualValue, String expectedValue){
			try {
				Assert.assertEquals(actualValue,expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value '" + actualValue + "' is same as Expected Value '" + expectedValue + "'");
			} catch (AssertionError error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), error.toString());
			}
		}
		public static void assertEquals(String actualValue, Object expectedValue){
			try {
				Assert.assertEquals(actualValue.trim().toUpperCase(),expectedValue);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value '" + actualValue + "' is same as Expected Value '" + expectedValue + "'");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), error.getMessage());
			}
		}
		
		public  void assertPresent(String textMessage){
			String xPath = "//*[text()='" + textMessage+ "']";
			try {
				Logger.info(getClassMethodName());
				WebDriverWait dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
				dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
				Reporter.ReportEvent(Status.PASS, getClassMethodName(),	"Expected text message '" + textMessage + "' has been found");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Expected text message '" + textMessage + "' not found");
			}
		}
		
		public void assertPresent(String xPath,String textMessage){
			try {
				Logger.info(getClassMethodName());
				WebDriverWait dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
				dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
				Reporter.ReportEvent(Status.PASS, getClassMethodName(),	"Expected text message '" + textMessage + "' has been found");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Expected text message '" + textMessage + "' not found by using path '" + xPath +"'");
			}
		}
		
		public static void assertNotPresent(String xpath){
			WebElement webElement = null;
		    try {
		    	Logger.info(getClassMethodName());
		    	webElement = DriverFactory.getWebDriver().findElement(By.xpath(xpath));
		    	if((webElement!=null)|!(webElement.equals(null))){
		    		Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "WebElement present");
		    	}
		    } catch (NoSuchElementException e) {
		    	Reporter.ReportEvent(Status.PASS, getClassMethodName(), "WebElement not present");
		    }
		}

		public void assertObject(String objectName){
			try {
				Logger.info(getClassMethodName());
				WebDriverWait dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
				dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath(objectName)));
				Reporter.ReportEvent(Status.PASS, getClassMethodName(),	"Expected text message '" + objectName + "' has been found");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Expected text message '" + objectName + "' not found, Error Description:'"+error.toString() +"'");
			}
		}
		
		public static void assertNull(String objectName){
			try {
				Logger.info(getClassMethodName());
				Assert.assertNull(objectName);
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual value '" + objectName + "' is same as Expected Value '" + "NULL" + "'");
			} catch (Exception error) {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Expected text message '" + objectName + "' not found ");
			}
		}
		
		private static String getClassMethodName(){
			String className,classSimpleName,methodName,classMethodName;
			className = Thread.currentThread().getStackTrace()[1].getClassName();
			classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length-1];
			methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			classMethodName =  classSimpleName + ":" + methodName ;
			return classMethodName; 
		}


	}

