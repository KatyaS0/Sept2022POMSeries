package com.qa.hubspot.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import com.qa.hubspot.base.BaseTest;
import com.qa.hubspot.testlistners.ExtentReportListener;
import com.qa.hubspot.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(ExtentReportListener.class) //this is how you can add listeners directly in the test but if you want to apply for entire suite, you apply it in xml

@Epic("epic 100: define login page features...")
@Story("US 101: define the login page class features with title, forgot pwd link, and login fucntionality")
public class LoginPageTest extends BaseTest {
	
	@Description ("vefify LoginPage Title test")
	@Severity(SeverityLevel.NORMAL)
	@Test (priority=1)
	public void verifyLoginPageTitleTest() {
		String title = loginPage.getLoginPageTitle(); //login page contructior will be called form BasePage
		System.out.println("login page title is: " + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE); // this will maintain the value of constant. 
	}
	
	@Description ("vefify forgot pwd test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=2)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	@Severity(SeverityLevel.BLOCKER)
	@Description ("login page test with username and password")
	@Test (priority=3)
	public void loginTest() {
		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	// there should not be any driver in the tests like (driver. whatever)
	//no assertions should be in page class, they should be in testNG
	//page has to have driver 
	//Martil Fowler on page objects - read about it 
	
	

}
