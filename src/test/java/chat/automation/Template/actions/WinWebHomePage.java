package chat.automation.Template.actions;

import com.aventstack.extentreports.Status;

import chat.automation.Template.repository.WinWebHomePageOR;
import chat.automation.framework.helper.ActionsHelper;
import chat.automation.framework.utils.Reporter;
public class WinWebHomePage extends ActionsHelper {
	
	public static void navigateToAccountType(){
		Reporter.ReportEvent(Status.PASS, "In navigateToAccountType", "Verified");
		
	}
}
