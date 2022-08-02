package com.qa.hubspot.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BaseTest;

public class ProductInfoTest extends BaseTest {
	
	
	@BeforeClass
	public void productInfoSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void ProductInfoPageTitleTest () {
		accountsPage.doSearch("Imac");
		productInfoPage = accountsPage.selectProductFromResults("IMac"); 
		Assert.assertEquals(productInfoPage.getProductInfoPageTitle("IMac"), "IMac");  
	}
	
	
	@Test
	public void verifyProductInfoTest_MacBook() {
		String productName = "MacBook";
		Assert.assertTrue(accountsPage.doSearch(productName));
		productInfoPage = accountsPage.selectProductFromResults("MacBook Pro");
		
		Assert.assertTrue(productInfoPage.getProductImages() == 4);
		
		Map<String, String> productI nfoMap = productInfoPage.getProductInformation(); 
		System.out.println(productInfoMap);
		//so the hash Map path will be:
		//{Brand=Apple, Availability=In Stock,
		//price=$2,000.00, name=MacBook Pro,
		//Product Code=Product 18, Reward Points=800,
		// exTaxPrice=$2,000.00}
		
		Assert.assertEquals(productInfoMap.get("name"), "MacBook Pro"); //this is to verify whatever you need form HashMap
		Assert.assertEquals(productInfoMap.get("Brand"), "Apple"); 
		Assert.assertEquals(productInfoMap.get("price"), "$2,000.00"); 
		Assert.assertEquals(productInfoMap.get("Product Code"), "Product 18"); 
		Assert.assertEquals(productInfoMap.get("Reward Points"), "800"); 
		
	}
	
	//what if we want to check other products? We can repeat same test case.
	
	@Test
	public void verifyProductInfoTest_IMac() {
		String productName = "IMac";
		Assert.assertTrue(accountsPage.doSearch(productName));
		productInfoPage = accountsPage.selectProductFromResults("IMac");
		
		Assert.assertTrue(productInfoPage.getProductImages() == 3);
		
		Map<String, String> productInfoMap = productInfoPage.getProductInformation(); 
		System.out.println(productInfoMap);
		
		Assert.assertEquals(productInfoMap.get("name"), "IMac"); //this is to verify whatever you need form HashMap
		Assert.assertEquals(productInfoMap.get("Brand"), "Apple"); 
		Assert.assertEquals(productInfoMap.get("price"), "$100.00"); 
		Assert.assertEquals(productInfoMap.get("Product Code"), "Product 14"); 
		Assert.assertEquals(productInfoMap.get("Reward Points"), "800"); 
		
	}
	
	
	
	
	
	

}
