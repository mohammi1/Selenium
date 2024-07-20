package chat.automation.Template.stepDefinitions;

import chat.automation.Template.actions.AccountTypeHome;
import chat.automation.Template.actions.AccountType_AddNew;
import chat.automation.Template.actions.LoginWinWeb;
import chat.automation.Template.actions.WinWebHomePage;
import chat.automation.framework.utils.Reporter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class TemplateDefinitionFile {
	@Given("Login to Win Web Page {string}")
	public void login_to_win_web_page(String string) throws ClassNotFoundException {
		Reporter.startNode("Given", "Login to Win Web Page {string}");
		LoginWinWeb.LaunchURL();
	}

	@Given("Navigate to Invoicing -->LOV-->Account Types from Navbar menu")
	public void navigate_to_invoicing_lov_account_types_from_navbar_menu() throws ClassNotFoundException {
		Reporter.startNode("Given", "Navigate to Invoicing -->LOV-->Account Types from Navbar menu");
		WinWebHomePage.navigateToAccountType();
		AccountTypeHome.verifyNavigationAccountTypeHomePage();
	}

	@When("Click on AddNew btn")
	public void click_on_add_new_btn() throws ClassNotFoundException {
		Reporter.startNode("When", "Click on AddNew btn");
		AccountTypeHome.clickOnAddNew();
	}

	@When("Enter Account Type and Account Description and save")
	public void enter_account_type_and_account_description_and_save() throws ClassNotFoundException {
		Reporter.startNode("When", "Enter Account Type and Account Description and save");
		AccountType_AddNew.SaveAddNewAccountType();
	}
	@Then("click on Edit against created Account Type")
	public void click_edit_against_accType() throws ClassNotFoundException {
		Reporter.startNode("Then", "click on Edit against created Account Type");
		AccountType_AddNew.clickEditInAccTypeTable();
	}

}
