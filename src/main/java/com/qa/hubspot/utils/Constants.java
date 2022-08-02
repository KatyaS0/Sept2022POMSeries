package com.qa.hubspot.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	
	public static final String LOGIN_PAGE_TITLE ="Account Login";  // this will remove the hard courted values. 
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";
	public static final String ACCOUNTS_PAGE_HEADER = "Your store";
	public static final int ACCOUNTS_SECTIONS = 4;
	public static final String REGISTER_SHEET_NAME = "Untitled spreadsheet(1)";	
	public static final String ACCOUNT_SUCCESS_MESSG = "Your account has been created!";
	
	
	public static List<String>  getAccountSectionsList() {
		
		List<String> accountList = new ArrayList<String>();
		accountList.add("My account");
		accountList.add("My Orders");
		accountList.add("My Affilliaye Account");
		accountList.add("Newseller");

		return accountList;
	}


}
