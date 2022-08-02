package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.utils.Constants;
import com.qa.hubspot.utils.ElementUtil;

import io.netty.util.Constant;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
	
	private WebDriver driver; //so we can use this driver in page actions (get title, find element etc methods)
	private ElementUtil elementUtil;
	
	//1. define by locators( private or public)
	private By emailId = By.id("username");
	private By password = By.id("password");
	private By loginButton = By.xpath("//input[@value='Login'@ @type='submit']");
	private By forgotPwdLink = By.linkText("Forgotten password"); 
	
	private By registerLink = By.linkText("Register");
	
	//2. Create constructor of page class: We use constr. to get access in the tests  and to get titles as below, otherwise will give us null 
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver); // created object oof util class to call the methods, other NullPoitner 
		
	}
	//3. Page actions: behavior/features of the page in the form methods:
	
	@Step("getting the login page title ...")
	public String  getLoginPageTitle() {
		return elementUtil.waitForTitleToBePresent(Constants.LOGIN_PAGE_TITLE, 10);
		
	}
	@Step ("checking forgot pwd link is exist ..")
	public boolean isForgotPwdLinkExist() {
		//return driver.findElement(forgotPwdLink).isDisplayed();
		return elementUtil.doIsDisplayed(forgotPwdLink);
	}
	
//	public SignUpPage navigateToSignUpPage () {
//		if(isSignUpLinkExist()) {
//			driver.findElement(signUpLink).click();
//		}
//		return new SignUpPage(); 
	
	@Step ("login wiht usearname: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("login with: " +username + " and " + pwd);
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginButton).click();
		
		elementUtil.doSendKeys(emailId, username);	
		elementUtil.doSendKeys(password, pwd);		
		elementUtil.doClick(loginButton);		
	
		return new AccountsPage(driver); //this will give you home page class obj
	}

	//we do not have no driver. , because it is in utils, thats why we not calling it, thats the advantage of utils.
	@Step ("navigating tot he register page")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink); 
		return new RegisterPage(driver);
	
	
	}

}
