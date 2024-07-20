package chat.automation.Template.actions;

import com.aventstack.extentreports.Status;

import chat.automation.Template.repository.LoginWinWebOR;
import chat.automation.framework.helper.ActionsHelper;
import chat.automation.framework.utils.APIUtils;
import chat.automation.framework.utils.Environment;
import chat.automation.framework.utils.Reporter;
import io.restassured.response.Response;

public class LoginWinWeb extends ActionsHelper{
	public static void LaunchURL() {
		Reporter.ReportEvent(Status.PASS, "In LaunchURL", "Verified");
		
	}
}
