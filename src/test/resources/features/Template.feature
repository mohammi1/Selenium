Feature: A test case in a Framework Template
   @CLH
    Scenario Outline: Click on a link in a LOV Table
  	
  	Given Login to Win Web Page "<URL>"
    And Navigate to Invoicing -->LOV-->Account Types from Navbar menu
    When Click on AddNew btn
    And Enter Account Type and Account Description and save
    Then click on Edit against created Account Type
  
			Examples:
      |URL|AccountType|AccountDescription|
		 	|https://winwebtest.cleanharbors.com/portalLogin/Login.aspx|Two|Two|
		 	   
 @CLHOne
    Scenario Outline: Click on a link in a LOV Table
  	
  	Given Login to Win Web Page "<URL>"
  	Examples:
      |URL|
      |https://winwebtest.cleanharbors.com/portalLogin/Login.aspx|