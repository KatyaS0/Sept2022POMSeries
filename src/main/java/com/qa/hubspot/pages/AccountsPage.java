package com.qa.hubspot.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.utils.Constants;
import com.qa.hubspot.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage extends BasePage {
	private WebDriver driver; 
	private ElementUtil elementUtil;
	
	private By header = By.cssSelector("div#logo a");
	private By accountSectionsHeaders = By.cssSelector("div#content h2");
	private By searchText = By.cssSelector("div#search input[name='search']");
	private By searchButton = By.cssSelector("div#search button[type='button']");
	private By searchItemsResult = By.cssSelector(".product-layout .product-thumb");
	private By resultItems = By.cssSelector(".product-thumb h4 a");

	
	public AccountsPage(WebDriver driver) {
		this.driver = driver; //so we get the driver and give it to Utils here in this method
		elementUtil = new ElementUtil(this.driver);
	}
	
	@Step ("getting accounts page title...")
	public String  getAccountsPageTitle() {
		return elementUtil.waitForTitleToBePresent(Constants.ACCOUNTS_PAGE_TITLE, 10);
	}
	
	@Step ("getting the header title...")
	public String getHeaderValue() {
//		if(driver.findElement(header).isDisplayed()) {
			//return driver.findElement(header).getText();
		if(elementUtil.doIsDisplayed(header)) {
			return elementUtil.doGetText(header);
		}
		return null;
	}
	@Step ("getting total number of accounts secttions .")
	public int getAccountSectionsCount () {
		//return driver.findElements(accountSectionsHeaders).size();
		return elementUtil.getElements(accountSectionsHeaders).size();
	}
	
	@Step ("getting accounts secttions list form the My account page ...")
	public List<String> getAccountSectionsList() {
		List<String> accountsList = new ArrayList<>();
		List<WebElement> accSectionsList = elementUtil.getElements(accountSectionsHeaders);
		
		for(WebElement e : accSectionsList) {
			System.out.println(e.getText());
			accountsList.add(e.getText());
		}
		return accountsList;
	}
	
	@Step ("seraching a product with name: {0}")
	public boolean doSearch(String productName) {
//		driver.findElement(searchText).sendKeys(productName);
//		driver.findElement(searchButton).click();
		elementUtil.doSendKeys(searchText, productName);
		elementUtil.doClick(searchButton);
		if(elementUtil.getElements(searchItemsResult).size()>0) {
			return true;
		}
		return false;
	}
	
	@Step ("selecting a product with name form the results section: {0}")
	public ProductInfoPage selectProductFromResults(String productName) {
		List<WebElement> resultItemList = elementUtil.getElements(resultItems);
		System.out.println("total number of items discplayed" + resultItemList.size());
		for(WebElement e : resultItemList ) {
			if(e.getText().equals(productName)) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}
}

//also no drivers used due to Utils












