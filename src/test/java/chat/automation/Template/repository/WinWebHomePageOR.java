package chat.automation.Template.repository;

public class WinWebHomePageOR {
	public static String lnkInvoicing="(//a[@href='#'][normalize-space()='Invoicing'])[2]";
	public static String lnkLOV="(//a[@href='#'][normalize-space()='Invoicing'])[2]//parent::li//a[normalize-space()='LOV']";
	public static String lnkAccountType="(//a[normalize-space()='Invoicing'])[2]//parent::li//a[normalize-space()='LOV']//parent::li//a[normalize-space()='Account Types']";
}