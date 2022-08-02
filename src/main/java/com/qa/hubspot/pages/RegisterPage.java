package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.utils.Constants;
import com.qa.hubspot.utils.ElementUtil;

public class RegisterPage extends BasePage {
	
	ElementUtil elementUtil;
	//need to create locators 
	private By firstname = By.id("input-firstname");
	private By lastname = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//label[@class='radio-inline'][position ()=1]/input");  //yes 
	private By subscribeNo = By.xpath("//label[@class='radio-inline'][position ()=2]/input"); //No
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By accountSucessMsg = By.cssSelector("#constant h1");
	private By logoutLink = By.linkText("Logout"); 
	private By registerLink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}
	
	public boolean accountRegistration(String firstname, String lastname, String email, String telephone, String password, String subscribe) {
		
		elementUtil.doSendKeys(this.firstname,firstname);
		elementUtil.doSendKeys(this.lastname,lastname);
		elementUtil.doSendKeys(this.email,email);
		elementUtil.doSendKeys(this.telephone,telephone);
		elementUtil.doSendKeys(this.password,password);
		elementUtil.doSendKeys(this.confirmPassword,password);
		
		if(subscribe.equals("yes")) {
			elementUtil.doClick(subscribeYes);
		}else {
			elementUtil.doClick(subscribeNo);
		}

		elementUtil.doClick(agreeCheckBox);
		elementUtil.doClick(continueButton);
		
		String text = elementUtil.doGetText(accountSucessMsg);
		if(text.contains(Constants.ACCOUNT_SUCCESS_MESSG)) {
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			
			return true;
		}
		return false;
	}
	


	
	

}
