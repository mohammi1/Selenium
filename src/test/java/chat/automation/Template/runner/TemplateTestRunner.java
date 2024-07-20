package chat.automation.Template.runner;

import org.testng.annotations.Listeners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Listeners(chat.automation.framework.core.FrameworkListener.class)	
@CucumberOptions(tags = "@CLHOne", features ={"src/test/resources/features/Template.feature"}, 
								glue = {"chat.automation.framework.core","chat.automation.Template.stepDefinitions"},
								monochrome = true,
								plugin= {"pretty",
								         "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
								        }
								
	)

	public class TemplateTestRunner extends AbstractTestNGCucumberTests {
	       
	}

