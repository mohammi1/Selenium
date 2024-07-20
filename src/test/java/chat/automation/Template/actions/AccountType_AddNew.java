package chat.automation.Template.actions;
import java.util.concurrent.ThreadLocalRandom;
import com.aventstack.extentreports.Status;
import chat.automation.Template.repository.AccountType_AddnewOR;
import chat.automation.framework.helper.ActionsHelper;
import chat.automation.framework.utils.Reporter;

public class AccountType_AddNew extends ActionsHelper {
	public static void SaveAddNewAccountType() {
		Reporter.ReportEvent(Status.PASS, "In Save AddNewAccount Function", "Verified");
		
	}
	
	public static void clickEditInAccTypeTable() {
		Reporter.ReportEvent(Status.PASS, "In clickEditInAccTypeTable", "Verified");
	}
}
