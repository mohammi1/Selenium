package chat.automation.framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	public WebDriver driver;
	public static ThreadLocal<WebDriver> tlDriver= new ThreadLocal<>();
	
	public WebDriver init_driver(String browserType) {
		System.out.println("Browser Name:'"+ browserType +"'");
		switch(browserType){
		case "CHROME":		
			
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--headless");
			//options.addArguments("--no-sandbox");
			//options.addArguments("--disable-dev-shm-usage");
			//WebDriver driver = new ChromeDriver(options);
			options.setBrowserVersion("125");
			//co.setBinary("C:\\Users\\mohammi1\\Downloads\\chrome-win64\\chrome-win64\\chrome.exe");
			//op.addArguments("--remote-allow-origins=*");
			//WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(options));
			break;
		case "FF":
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
			break;
		case "EDGE":
			EdgeOptions eo = new EdgeOptions();
			eo.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
			//WebDriverManager.edgedriver().setup();
			//eo.setBrowserVersion("125");
			tlDriver.set(new EdgeDriver(eo));
		case "REMOTEEDGE":
			break;
		case "REMOTECHROME":
			break;
		case "REMOTEFF":
			break;
		case "API":
			break;
			
		default:
			Reporter.ReportEvent(Status.FAIL, "Pass the appropriate Browser Type to Launch Browser", "Wrong Browser Type, Please recheck");
		}
		getWebDriver().manage().deleteAllCookies();
		getWebDriver().manage().window().maximize();
		return getWebDriver();
	}
	
	public static synchronized WebDriver getWebDriver() {
		WebDriver myDriver;
		myDriver=tlDriver.get();
		return myDriver;
		
	}

}
