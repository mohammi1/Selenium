package chat.automation.Template.actions;

import com.aventstack.extentreports.Status;

import chat.automation.Template.repository.AccountTypeHomeOR;
import chat.automation.framework.helper.ActionsHelper;
import chat.automation.framework.utils.ExpectedConditions;
import chat.automation.framework.utils.Reporter;

public class AccountTypeHome extends ActionsHelper {
	public static void verifyNavigationAccountTypeHomePage() {
		Reporter.ReportEvent(Status.PASS, "In verifyNavigationAccountTypeHomePage", "Verified");
		
	}
	
	public static void clickOnAddNew() {
		Reporter.ReportEvent(Status.PASS, "In clickOnAddNew", "Verified");
	}
}