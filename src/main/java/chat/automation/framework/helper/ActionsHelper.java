package chat.automation.framework.helper;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import chat.automation.framework.exceptions.FrameworkExceptions;
import chat.automation.framework.utils.DriverFactory;
import chat.automation.framework.utils.FrameworkConstants;
import chat.automation.framework.utils.Logger;
import chat.automation.framework.utils.Reporter;
import chat.automation.framework.utils.ExpectedConditions;

public class ActionsHelper {

	private static void waitForPageToLoad() {
		Logger.startMethod();
		JavascriptExecutor js = null;
		String pageStatus = null;
		long startTime,maxTimeOut;
		try {
			maxTimeOut = FrameworkConstants.maxTimeOut*1000;
			js = (JavascriptExecutor) DriverFactory.getWebDriver();
			pageStatus = js.executeScript("return document.readyState").toString();
			startTime = System.currentTimeMillis();
			while((!(pageStatus.equalsIgnoreCase("complete")))&(System.currentTimeMillis() - startTime<=maxTimeOut)){
				pageStatus = js.executeScript("return document.readyState").toString();
			}
			if(!(pageStatus.equalsIgnoreCase("complete"))){
				Reporter.ReportEvent(Status.WARNING, getClassMethodName(), "Page is not loaded completely");
			}
		}catch(Exception exception){
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	private static WebElement getObject(String byXpath) {
		Logger.startMethod();
		String screenName = null;
		String logicalName = null;
		String[] className = null;
		WebDriver driver = DriverFactory.getWebDriver();
		className	= Thread.currentThread().getStackTrace()[3].getClassName().replace(".", "/").split("/");
		screenName = className[className.length-1];
		logicalName = Thread.currentThread().getStackTrace()[3].getMethodName();
		WebElement webElement = null;
		try {
			waitForPageToLoad();
			List<WebElement> webElements = driver.findElements(By.xpath(byXpath));
			if(webElements.size()>0){
				webElement = driver.findElement(By.xpath(byXpath));
			}
		}catch(Exception exception){
			Reporter.ReportEvent(Status.FAIL,"getobject","Object'" + logicalName + "' Unable to convert to WebElement" + screenName + " by using xPath '" + byXpath + "'");
			Assert.assertTrue(false);
		}
		
		Logger.endMethod();
		return webElement;
		
	}
	
	public static WebElement findObject(String byXpath) {
		Logger.startMethod();
		String screenName = null;
		String logicalName = null;
		String[] className = null;
		WebDriver driver = DriverFactory.getWebDriver();
		className	= Thread.currentThread().getStackTrace()[3].getClassName().replace(".", "/").split("/");
		screenName = className[className.length-1];
		logicalName = Thread.currentThread().getStackTrace()[3].getMethodName();
		WebElement webElement = null;
		WebDriverWait dynamicWait = null;
		long startTime,objectTimeOut;
		try {
			waitForPageToLoad();
			objectTimeOut = FrameworkConstants.maxTimeOut * 1000;
			startTime = System.currentTimeMillis();
			List<WebElement> webElements = driver.findElements(By.xpath(byXpath));
			while((webElements.size()==0)&(System.currentTimeMillis() - startTime<=objectTimeOut)){
				Thread.sleep(1000);
				webElements = driver.findElements(By.xpath(byXpath));
			}
			if(webElements.size()>0){
				webElement = driver.findElement(By.xpath(byXpath));
				dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.maxTimeOut));
				dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(webElement));
				dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath(byXpath)));
				Logger.info("Object'" + logicalName + "' found in page" + screenName + " by using xPath '" + byXpath + "'");
			}else{
				Reporter.ReportEvent(Status.FAIL,"findObject","Object'" + logicalName + "' not found in page" + screenName + " by using xPath '" + byXpath + "'");
				Assert.assertTrue(false);
			}
			
		}catch(Exception exception){
			Reporter.ReportEvent(Status.FAIL,"findObject","Object'" + logicalName + "' not found in page" + screenName + " by using xPath '" + byXpath + "'");
			Assert.assertTrue(false);
		}
		Logger.endMethod();
		return webElement;
	}
	protected static void verifyTitle(String expectedTitle){
		Logger.startMethod();
		waitUntilPageLoaded();
		String actualTitle = DriverFactory.getWebDriver().getTitle();
		try {
			ExpectedConditions.assertEquals(actualTitle,expectedTitle);
			Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual title '" + actualTitle + "' is same as Expected '" + expectedTitle + "' title");
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Actual title '" + actualTitle + "' is not same as Expected '" + expectedTitle + "' title");
		}
		Logger.endMethod();
	}
	
	protected static void verifyObjectExists(String xpath,String reportMessage){
		Logger.startMethod();
		WebElement webElement = null;
		try {
			webElement = findObject(xpath);
			Assert.assertTrue(isDisplayed(webElement));
			Reporter.ReportEvent(Status.PASS, getClassMethodName(), reportMessage +",  ['"+ xpath +"'] found in the Application");
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), reportMessage +",  ['"+ xpath +"'] not found in the Application");
		}
		Logger.endMethod();
	}
	
	protected static boolean exist(String xpath){
		Logger.startMethod();
		WebElement webElement = null;
		WebDriverWait dynamicWait=null;
		webElement=findObject(xpath);
		try {
			dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
			dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(webElement));
			Logger.endMethod();
			return true;
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Object not found:'"+xpath);
			Logger.endMethod();
			return false;
		}
		
	}
	protected static boolean exist(WebElement webElement){
		Logger.startMethod();
		WebDriverWait dynamicWait=null;
		try {
			dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
			dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(webElement));
			Logger.endMethod();
			return true;
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Object not found:'"+webElement.getText());
			Logger.endMethod();
			return false;
		}
		
	}
	
	public static void handleWindow() {
		Logger.startMethod();
		String currentWindow=DriverFactory.getWebDriver().getWindowHandle();
    	for (String handle : DriverFactory.getWebDriver().getWindowHandles()) { //Gets the new window handle
    		if(!handle.equals(currentWindow)) {
    			DriverFactory.getWebDriver().switchTo().window(handle);
    		}             
        }
    	Logger.endMethod();
    }
    
    public static void Refresh() {
    	Logger.startMethod();
    	DriverFactory.getWebDriver().navigate().refresh();       // switch focus of WebDriver to the next found window handle (that's your newly opened window)  
    	Logger.endMethod();
        }
	
	protected static void verifyObjectExists(WebElement webElement,String reportMessage){
		Logger.startMethod();
		try {
			Assert.assertTrue(isDisplayed(webElement));
			Reporter.ReportEvent(Status.PASS, getClassMethodName(), reportMessage);
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), reportMessage);
		}
	}
		
	public static void verifyText(String object, String expectedText) {
		Logger.startMethod();
		try {
			WebElement webElement = findObject(object);
			ExpectedConditions.assertEquals(webElement.getText(), expectedText); // (webElement.getText(), expectedText);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	public static void verifyText(WebElement webElement, String expectedText) {
		Logger.startMethod();
		try {
			ExpectedConditions.assertEquals(webElement.getText(), expectedText); // (webElement.getText(), expectedText);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	public static void verifyErrorMessage(String errorMessage) {
		Logger.startMethod();
		try {
			String xPath = "//*[contains(text(),'" + errorMessage + "')]";
			WebElement webElement = findObject(xPath);
			Assert.assertNotNull(webElement);
			Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Error Message '" + errorMessage + "' found in application");
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Error Message '" + errorMessage + "' not found in application");
			Reporter.exitTest();
		}
		Logger.endMethod();
	}
	
	public static void verifyMessage(String displayMessage) {
		Logger.startMethod();
		try {
			String xPath = "//*[contains(text(),'" + displayMessage + "')]";
			System.out.println(xPath);
			WebElement webElement = findObject(xPath);
			Assert.assertNotNull(webElement);
			Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Message '" + displayMessage + "' found in application");
		} catch (Error error) {
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Message '" + displayMessage + "' not found in application");
			Reporter.exitTest();
		}
		Logger.endMethod();
	}
	
	protected static void verifyTextContains(String xPath, String expectedText) {
		Logger.startMethod();
		try {
			WebElement webElement = findObject(xPath);
			String actualText= webElement.getText();
			if(actualText.toUpperCase().contains(expectedText.toUpperCase())) {
				Reporter.ReportEvent(Status.PASS, getClassMethodName(), "Actual Text '" + actualText + "' contains Expected Text '" + expectedText + "'");
			}else {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Actual Text '" + actualText + "' doesnot contains Expected Text '" + expectedText + "'");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	
	/* Check whether webelement is null or not */
	public static boolean isNotNull(WebElement webElement) {
		Logger.info(getClassMethodName());
		try {
			if ((webElement == null) | ("null".equals(webElement))) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
			return false;
		}
	}
	
	/* Check whether webElement condition */
	public static  boolean waitForCondition(WebElement webElement) {
		Logger.info(getClassMethodName());
		boolean isEnabled = false;
		boolean isDisplayed = false;
		boolean condition = false;
		try {
			if (isNotNull(webElement)) {
				isDisplayed = webElement.isDisplayed();
				if(isDisplayed){
					isEnabled = webElement.isEnabled();
					if (isEnabled) {
						condition = true;
					}
				}
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return condition;
	}
	
	public static  boolean waitForCondition(String object) {
		Logger.info(getClassMethodName());
		boolean isEnabled = false;
		boolean isDisplayed = false;
		boolean condition = false;
		WebElement webElement=findObject(object);
		try {
			if (isNotNull(webElement)) {
				isDisplayed = webElement.isDisplayed();
				if(isDisplayed){
					isEnabled = webElement.isEnabled();
					if (isEnabled) {
						condition = true;
					}
				}
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return condition;
	}
	
	/* Wait for Page to Load */
	public static void waitUntilPageLoaded() {
		Logger.startMethod();
		JavascriptExecutor js = null;
		String pageStatus = null;
		long startTime;
		try {
			js = (JavascriptExecutor) DriverFactory.getWebDriver();
			pageStatus = js.executeScript("return document.readyState").toString();
			startTime = System.currentTimeMillis();
			while((!(pageStatus.equalsIgnoreCase("complete")))&(System.currentTimeMillis() - startTime<=FrameworkConstants.maxTimeOut*1000)){
				Thread.sleep(1000);
				pageStatus = js.executeScript("return document.readyState").toString();
			}
			if(!(pageStatus.equalsIgnoreCase("complete"))){
				Reporter.ReportEvent(Status.WARNING, getClassMethodName(), "Page is not loaded completely");
			}
		}catch(Exception exception){
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	/* Wait until WebElement is visible */
	public static void waitUntilVisible(WebElement webElement) {
		Logger.info(getClassMethodName());
		WebDriverWait dynamicWait = null;
		try {
			dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
			dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(webElement));
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	
	/* Wait until Frame to be available */
	public static void waitUntilFrameAvailable(WebElement webElement) {
		Logger.info(getClassMethodName());
		WebDriverWait dynamicWait = null;
		try {
			dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
			dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt(webElement));
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	/* check whether WebElement is enabled or not */
	public static boolean isEnabled(WebElement webElement) {
		Logger.info(getClassMethodName());
		boolean isEnabled = false;
		try {
			isEnabled = webElement.isEnabled();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return isEnabled;
	}
	
	public static boolean isDisplayed(WebElement webElement) {
		Logger.info(getClassMethodName());
		boolean isDisplayed = false;
		try {
			isDisplayed = webElement.isDisplayed();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return isDisplayed;
	}
	
	public static boolean isDisplayedAndEnabled(WebElement webElement){
		Logger.info(getClassMethodName());
		boolean isEnabledAndDisplayed = false;
		boolean isEnabled = false;
		boolean isDisplayed = false;
		try {
			isEnabled = webElement.isEnabled();
			isDisplayed = webElement.isDisplayed();
			if(isDisplayed & isEnabled){
				isEnabledAndDisplayed = true;
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return isEnabledAndDisplayed;
	}
	
	public static boolean isSelected(WebElement webElement) {
		Logger.info(getClassMethodName());
		boolean isSelected = false;
		try {
			isSelected = webElement.isSelected();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return isSelected;
	}

	
	/* Clicks on the object */
	protected static void clickObject(String object) {
		Logger.startMethod();
		WebElement webElement = findObject(object);
		try {
			if (waitForCondition(webElement)) {
				waitUntilVisible(webElement);
				webElement.click();
				Reporter.ReportEvent(Status.INFO, getClassMethodName(),"Clicked on '" + object + "'" + " Sucessfully");
			} else {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement'" + object + "'is null/disable/not visiable");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	/* Clicks on the object */
	protected static void clickObject(WebElement webElement) {
		Logger.startMethod();
		try {
			if (waitForCondition(webElement)) {
				waitUntilVisible(webElement);
				webElement.click();
				Reporter.ReportEvent(Status.INFO, getClassMethodName(),"Clicked on '" + webElement + "'" + " Sucessfully");
			} else {
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement'" + webElement + "'is null/disable/not visiable");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
		public static List<WebElement> findElements(String xPath) {
		Logger.info(getClassMethodName());
		List<WebElement> webElements = null;
		try {
			webElements = DriverFactory.getWebDriver().findElements(By.xpath(xPath));
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return webElements;
	}
	
		/* Sets the text in the text field */
	protected static void setValue(String object, String text) {
		Logger.startMethod();
		try {
			WebElement webElement = findObject(object);
			if (waitForCondition(webElement)) {
				webElement.sendKeys(text);
				Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"'" + text + "'" +" is entered in the Text Box '");
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/disable/not visiable");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	protected static void setValue(WebElement object, String text) {
		Logger.startMethod();
		try {
			
			if (waitForCondition(object)) {
				object.sendKeys(text);
				Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"'" + text + "'" +" is entered in the Text Box '");
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/disable/not visiable");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		Logger.endMethod();
	}
	
	
	/* Gets the text that is displayed in the text field */
	public static String getText(WebElement webElement) {
		Logger.info(getClassMethodName());
		String controlText = null;
		try {
			if (isNotNull(webElement)) {
				controlText = webElement.getText();
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/not visiable");
			}
			return controlText;
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
			return null;
		}
	}
	/* Gets the text that is displayed in the text field */
	public static String getText(String byXpath) {
		Logger.info(getClassMethodName());
		String controlText = null;
		try {
			WebElement webElement=findObject(byXpath);
			if (isNotNull(webElement)) {
				controlText = webElement.getText();
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/not visiable");
				Reporter.exitTest();
			}
			return controlText;
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
			return null;
		}
	}
		
	public static void switchToParentFrame() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().switchTo().parentFrame();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	public static void switchToFrame(int index) {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().switchTo().frame(index);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	public static void switchToFrame(String name) {
		
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().switchTo().frame(name);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}

	}
	
	public static void switchToDefault() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().switchTo().defaultContent();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	
	
	public static void objectSync() {
		try {
			Logger.info(getClassMethodName());
			WebDriverWait dynamicWait = new WebDriverWait(DriverFactory.getWebDriver(),Duration.ofSeconds(FrameworkConstants.maxTimeOut));
			dynamicWait.wait(FrameworkConstants.maxTimeOut);
			//DriverFactory.getWebDriver().manage().timeouts().implicitlyWait(FrameworkConstants.maxTimeOut,TimeUnit.SECONDS);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	public static void pageSync() {
		JavascriptExecutor js = null;
		String pageStatus = null;
		long startTime;
		try {
			Logger.info(getClassMethodName());
			js = (JavascriptExecutor) DriverFactory.getWebDriver();
			pageStatus = js.executeScript("return document.readyState").toString();
			startTime = System.currentTimeMillis();
			while((!(pageStatus.equalsIgnoreCase("complete")))&(System.currentTimeMillis() - startTime<=FrameworkConstants.maxTimeOut)){
				Thread.sleep(1000);
				pageStatus = js.executeScript("return document.readyState").toString();
			}
		}catch(Exception exception){
			new FrameworkExceptions(exception);
		}
	}
	
		/* Loads the given URL into the browser. */
	public static void open(String URL) {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().get(URL);
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Load a new web page [" + URL + "] in the current browser window");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	/* Refreshes the current browser */
	public static void refresh() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().navigate().refresh();
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Refreshes the current browser");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	/* Maximize the current browser */
	public static void maximize() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().manage().window().maximize();
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Maximize the current browser");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	/* Close the browser window (or tab) */
	public static void close() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().close();
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Close the latest current browser window (or tab)");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	/* Close the browser window and all tabs if any */
	public static void quit() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().quit();
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Close the browser window and all tabs if any");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}

	/* Clear all of the browser's cookies */
	public static void clearCookies() {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().manage().deleteAllCookies();
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Clear all of the browser's cookies");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	/* Clear browser cookie specified by the name of cookie */
	public static void clearCookie(String cookieName) {
		try {
			Logger.info(getClassMethodName());
			DriverFactory.getWebDriver().manage().deleteCookieNamed(cookieName);
			Reporter.ReportEvent(Status.INFO, getClassMethodName() ,"Clear the named [" + cookieName + "] cookie from the current domain");
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	
	public static String getUrl() {
		String currentURL = null;
		try {
			Logger.info(getClassMethodName());
			currentURL =  DriverFactory.getWebDriver().getCurrentUrl();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return currentURL;
	}
	
	public static String getProperty(WebElement webElement, String propertyName) {
		Logger.info(getClassMethodName());
		String propertyValue = null;
		try {
			
			if (isNotNull(webElement)) {
				propertyValue = webElement.getAttribute(propertyName);
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/disable/not visiable");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return propertyValue;
	}
	public static String getProperty(String byXPath, String propertyName) {
		Logger.info(getClassMethodName());
		String propertyValue = null;
		try {
			WebElement webElement=findObject(byXPath);
			if (isNotNull(webElement)) {
				propertyValue = webElement.getAttribute(propertyName);
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/disable/not visiable");
				Reporter.exitTest();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		return propertyValue;
	}
	
	public static void verifyIsEnabled(WebElement webElement,String expectedValue){
		Logger.info(getClassMethodName());
		try {
			ExpectedConditions.assertEquals(Boolean.toString(isEnabled(webElement)), expectedValue);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	public static void verifyIsEnabled(String byXpath,String expectedValue){
		Logger.info(getClassMethodName());
		try {
			WebElement webElement=findObject(byXpath);
			ExpectedConditions.assertEquals(Boolean.toString(isEnabled(webElement)), expectedValue);
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
	}
	
	public static int getRowCount(WebElement table) {
		Logger.info(getClassMethodName());
		int rowCnt=-1;
		try {
			
			if (isNotNull(table)) {
				List<WebElement> rows=table.findElements(By.tagName("tr"));
				rowCnt = rows.size();
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/disable/not visiable");
			}
			
		} catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
		return rowCnt;
	}
	
	public static int getRowCount(String xPath) {
		Logger.info(getClassMethodName());
		int rowCnt=-1;
		try {
			
			WebElement table=findObject(xPath);
			if (isNotNull(table)) {
				List<WebElement> rows=table.findElements(By.tagName("tr"));
				rowCnt = rows.size();
			}else{
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"WebElement is null/disable/not visiable");
				Reporter.exitTest();
			}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
		return rowCnt;
	}
	
	public static int getRowWithCelltext(String tableXpath, String expectedText) {
		Logger.info(getClassMethodName());
		int rowNumber=-1;
		try {
			WebElement table=findObject(tableXpath);
			boolean isTableExist=isNotNull(table);
			if (!isTableExist){
				Reporter.exitTest();
			}
			int rowCnt=getRowCount(table);
			if (rowCnt>0) {
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				for (int i = 0; i < rowCnt; i++) {
					List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
					for (WebElement cell : cells) {
						 if (cell.getText().trim().toUpperCase().equals(expectedText.trim().toUpperCase())) {
							 System.out.println("Actual:"+cell.getText().trim().toUpperCase());
							 System.out.println("Expected:"+expectedText.trim().toUpperCase());
							 rowNumber=i+1; 
				 			 break;
						 }
					}
					if (rowNumber != -1) {
		                break;
					}
				}
			}else {
				Reporter.ReportEvent(Status.FAIL, "Get row count in the Table", "No records found in the Table");
			}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
			
		return rowNumber;
	}
	
	public static int getColumnNumber(String tableXpath, String columnName) {
		Logger.info(getClassMethodName());
		int columnNumber=-1;
		try {
			
			WebElement table=findObject(tableXpath);
				List<WebElement> cols = table.findElements(By.tagName("th"));
				if(cols.size()>0) {
					for (int i = 0; i < cols.size(); i++) {
						if(cols.get(i).getText().toUpperCase().trim().equals(columnName.toUpperCase().trim())) {
							columnNumber=i+1;
							return columnNumber;
						}
					}
				}
				else {
					Reporter.ReportEvent(Status.FAIL, "get Column Number by passing Column Name", "No columns exists in the Table '" + tableXpath +"'");
					Reporter.exitTest();
				}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
			
		return columnNumber;
	}
		
	public static int getColumnNumber(WebElement tableRef, String columnName) {
		Logger.info(getClassMethodName());
		int columnNumber=-1;
		try {
				List<WebElement> cols = tableRef.findElements(By.tagName("th"));
				if(cols.size()>0) {
					for (int i = 0; i < cols.size(); i++) {
						if(cols.get(i).getText().toUpperCase().trim().equals(columnName.toUpperCase().trim())) {
							columnNumber=i+1;
							return columnNumber;
						}
					}
				}
				else {
					Reporter.ReportEvent(Status.FAIL, "get Column Number by passing Column Name", "No columns exists in the Table '" + tableRef.getText() +"'");	
				}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
		return columnNumber;
	}	

	public static String getCellData(WebElement tableRef, int rowNumber, int colNumber) {
		Logger.info(getClassMethodName());
		String cellData =null;
		try {
			List<WebElement> rows = tableRef.findElements(By.tagName("tr"));
			if(rows.size()>0) {
					List<WebElement> col=rows.get(rowNumber).findElements(By.tagName("td"));
					if(col.size()>0) {
						cellData=col.get(colNumber).getText();
				}
			}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
		return cellData;
	}
	public static String getCellData(String tableXpath, int rowNumber, int colNumber) {
		String cellData =null;
		try {
			WebElement tableRef=findObject(tableXpath);
				List<WebElement> rows = tableRef.findElements(By.tagName("tr"));
				if(rows.size()>0) {
						List<WebElement> col=rows.get(rowNumber-1).findElements(By.tagName("td"));
						if(col.size()>0) {
							cellData=col.get(colNumber-1).getText();
					}
				}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
		return cellData;
	}
	
	public static void webTableClickObject(String tableXpath,String objectTag, int rowNumber, int colNumber) {
		Logger.info(getClassMethodName());
		boolean isExist=false;
		try {
			String selectObject=tableXpath +"/tbody/tr["+ rowNumber +"]/td["+ colNumber +"]/"+objectTag; 
			WebElement selectObjectRef=findObject(selectObject);
			isExist=isNotNull(selectObjectRef);
			System.out.println("WebTableSelectobject"+isExist);
			if(isExist) {
				selectObjectRef.click();
				Reporter.ReportEvent(Status.PASS, "Verify object'"+selectObject.toString(), "Object exist and performed click operation");
			}else {
				Reporter.ReportEvent(Status.FAIL, "Verify object'"+selectObject.toString(), "Object doesnot exist/Unable to perform an action");
			}
		}catch(Exception exception) {
			new FrameworkExceptions(exception);
		}
		
	}
	public static void OnTerminate() {	
		Logger.info(getClassMethodName());
		   DriverFactory.getWebDriver().quit();
		   System.out.println("This is After-On Termination");
		   //System.exit(0);
		}
	
	public static void waitUntilObjectDisappears(String loaderXpath) throws InterruptedException {
		Logger.info(getClassMethodName());
		WebElement getLoader=getObject(loaderXpath);		
		long startTime = System.currentTimeMillis();
		long maxTimeOut = FrameworkConstants.maxTimeOut*1000;
		boolean isExist=isDisplayedAndEnabled(getLoader);
		while((isExist) &(System.currentTimeMillis() - startTime<=maxTimeOut)){
			Thread.sleep(1000);
			isExist=isDisplayedAndEnabled(getLoader);
		}
		if(isExist){
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(), "Page is not loaded completely");
			Reporter.exitTest();
		}
	}
	
	public static String handleAlertgetText() {
		Logger.info(getClassMethodName());
		String alertMsg="";
		try {
			Thread.sleep(2000);
			 Alert confirmAlert = DriverFactory.getWebDriver().switchTo().alert();
			 alertMsg=confirmAlert.getText();
			 confirmAlert.accept();
			 boolean isExist= isAlertPresent();
			 if(isExist) {
				 Reporter.ReportEvent(Status.FAIL, "Verify Alert", "Unknown error occured/Unable to close the Browser Alert");
				 Reporter.exitTest();
			 }
		}catch (Exception e){
			new FrameworkExceptions();
		}
		
		 return alertMsg;
	}
	public static boolean isAlertPresent() {
		Logger.info(getClassMethodName());
		boolean isStatus=false;
		WebDriver driver=DriverFactory.getWebDriver();
        try {
        	WebDriverWait dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.minTimeOut));
            dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
            return isStatus;
        } catch (Exception e) {
        	new FrameworkExceptions();
        	
        }
        return false;
    }
	
	public static WebElement findElementInIFrames(String xPath) {
		Logger.info(getClassMethodName());
		
			WebDriver driver=DriverFactory.getWebDriver();
	        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
	
	        for (int i = 0; i < iframes.size(); i++) {
	            driver.switchTo().frame(iframes.get(i));
	            try {
	                WebElement element = driver.findElement(By.xpath(xPath));
	                return element; // Element found, return it
	            } catch (Exception exception) {
	            	new FrameworkExceptions(exception);
               
                driver.switchTo().defaultContent();
	            }
            }

        // Element not found in any iframe, return null
        return null;
    }
	 public static String convertToString(Object obj) {
		 try {
			 	 
	        if (obj instanceof String) {
	            return (String) obj;
	        } else if (obj instanceof Float) {
	            return Float.toString((Float) obj);
	        } else if (obj instanceof Integer) {
	            return Integer.toString((Integer) obj);
	        } else if (obj instanceof Double) {
	            return Double.toString((Double) obj);
	        } else if (obj instanceof Boolean) {
	            return Boolean.toString((Boolean) obj);
	        } else {
	        	Reporter.ReportEvent(Status.FAIL, "Unsupported type: " + obj.getClass().getName(), "Please check and pass correct data type");
	            return null;
	        }
		 }catch(Exception e) {
			 Reporter.ReportEvent(Status.FAIL, "Unsupported type: " + obj.getClass().getName(), "Following Error:'"+e.toString() +"' displayed");
		 }
		 return null;
	}

	
		private static String getClassMethodName() {
		String className, classSimpleName, methodName, classMethodName;
		className = Thread.currentThread().getStackTrace()[1].getClassName();
		classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length - 1];
		methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		classMethodName = "'" + classSimpleName + ":" + methodName + "'";
		return classMethodName;
	}

}