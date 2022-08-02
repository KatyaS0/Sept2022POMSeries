package com.qa.hubspot.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BaseTest;
import com.qa.hubspot.utils.Constants;
import com.qa.hubspot.utils.ExcelUtil;


public class RegisterPagetest extends BaseTest {
	
	
	@BeforeClass
	public void registerPageSetUp() {
		registerPage = loginPage.navigateToRegisterPage(); //this will give you navigateToRegisterPage object 
	}
	
	@DataProvider //catches data from excel sheet.
	public Object[][] getRestisterData() {
		Object data [][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return data;
		
	}
	
	@Test(dataProvider = "getRegisterData") //where are you getting the data 
	public void userRegistrationTest(String firstname, String lastname, String email, String telephone, String password, String subscribe) {//needs whatever in () ti hold teh data 
		registerPage.accountRegistration(firstname, lastname, email, telephone, password, subscribe);
	}//this will be executed depending on total # of rows

}
//Objext[][] - two obj array 
//but if you run this one more time, they would not run again. You would have to update the excel sheet everytime or you can generate random things tru random generator 
//Parallel activity is a testNG Listner- it will listen to the test cases you executing and ask whats the name of the test and whats the status of the result. What is the name and result of the test cases
//It will capyure the result and method name, It has certain methods. And it asks what exactly do you want when your test cases are getting passed. If lets say one of the tests
//failed, a particular methid will be called in the listener automatically, in case if fail pass etc. Whatever methid it has in the body, it just has its own. It wuns on your background of your execution. 
//Allure report listenr: will generate their own html report once all tests are done. It has the same consept of the listner. 
//extends lister: same concept.There are just many listeners. Its available in JAVA class. You can use all of them but better to use one lister type. The reporst will have same data
//reports will be in built folder that will be created autoomatically after you run the test from regression. 
//we have to put the listener in the xml file and you have to execute it form the xml file. But you can attach the listener  into the test (LoginPageTest)
