package com.qa.hubspot.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BaseTest;
import com.qa.hubspot.pages.AccountsPage;
import com.qa.hubspot.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("US 200: Design Accounts Page")
@Story("US 201: designign the accounts page with title, header, accounts seactoins and product results...")
public class AccountsPageTest extends BaseTest{
	
	@BeforeClass	
	public void  AccountsPageSetUp () { // we don't write the login for homePage in base, because it will disturb what base already has so we hazve to do it here
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description ("Accounts Page Title test")
	@Severity(SeverityLevel.NORMAL) // this is given by allure report 
	@Test(priority = 1)
	public void accountsPageTitletest () {
		String title = accountsPage.getAccountsPageTitle();
		System.out.println("accounts page title is: " + title);
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Description ("accounts page header test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void verifyAccountspageHeaderTest() {
		String headerValue = accountsPage.getHeaderValue();
		System.out.println ("accounts page header is: " + headerValue);
		Assert.assertEquals(headerValue, Constants.ACCOUNTS_PAGE_HEADER);

	}
	
	@Description ("accounts page sections count test")
	@Severity(SeverityLevel.NORMAL)
	@Test (priority=3)
	public void verifyMyAccountSectionsCountTest() {
		Assert.assertTrue(accountsPage.getAccountSectionsCount() == Constants.ACCOUNTS_SECTIONS);
	}
	
	@Description ("accounts page sections list test")
	@Severity(SeverityLevel.CRITICAL)
	@Test (priority = 4)
	public void verifyMyAccountSectionsListtest() {
		Assert.assertEquals(accountsPage.getAccountSectionsList(), Constants.getAccountSectionsList());//we are comparing two array lists here (expected and actual list	}
	}
	
	@Description ("accounts page search test")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void searchTest() {
		Assert.assertTrue(accountsPage.doSearch("Imac"));
	}
}
