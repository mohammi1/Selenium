package chat.automation.framework.core;
import org.openqa.selenium.WebDriver;

import chat.automation.framework.exceptions.FrameworkExceptions;
import chat.automation.framework.utils.DriverFactory;
import chat.automation.framework.utils.Environment;
import chat.automation.framework.utils.Reporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
	
public class FrameworkHooks {
	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
	private DriverFactory driver;
	@Before
	public void OnInitiation(Scenario scenario) throws ClassNotFoundException {
		Reporter.SetScenario(scenario);
		
	   String ScenarioName=Reporter.getScenarioName();	
	   String FeatureName=Reporter.getFeatureName();
	   System.out.println("beforeScenario: Scenarion name"+ ScenarioName);
	   System.out.println("beforeScenario: Feature name"+ FeatureName);
	   System.setProperty("ScenarioName", ScenarioName);
	   Reporter.startTest(ScenarioName);
	   //Reporter.startNode("GIVEN", "Started Node");
	   String browserType=Environment.getProperty("BrowserType");
	   driver= new DriverFactory();
	   if(!browserType.trim().toUpperCase().equals("API")) {
		   driver.init_driver(Environment.getProperty("BrowserType").trim());
		   System.out.println(" On Initialization Browser Name:"+ browserType);
	   }
	   
	   System.out.println("This is Before Test-On Initialization");   	  
	 }

	@After
	public void OnTermination() {
		System.out.println("Hooks-On Termination");
		try {
			if (DriverFactory.getWebDriver() != null) {
				DriverFactory.getWebDriver().quit();
			}
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
		}
		//DriverFactory.getWebDriver().quit();
	   threadLocalDriver.remove();
	   
	}
	
}
