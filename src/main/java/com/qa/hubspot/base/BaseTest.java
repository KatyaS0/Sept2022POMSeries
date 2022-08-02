package com.qa.hubspot.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.beust.jcommander.Parameters;
import com.qa.hubspot.pages.AccountsPage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.pages.ProductInfoPage;
import com.qa.hubspot.pages.RegisterPage;

public class BaseTest {
	
	
	public BasePage basePage;
	public LoginPage loginPage;
	public AccountsPage accountsPage;
	public ProductInfoPage productInfoPage;
	public RegisterPage registerPage ;

	public Properties prop;
	public WebDriver driver;
	
	@Parameters({"browser", "version"})
	@BeforeTest
	public void setUp(String browserName, String browserVersion) {
		
		basePage = new BasePage();
		prop = basePage.init_prop(); 
		String browser = prop.getProperty("browser");
		
		if(browserName!=null) {
			browser=browserName;
		}
		
		driver = basePage.init_driver(browser, browserVersion); // we have to get the driver first, and then we will be able to get the title. 
		loginPage = new LoginPage(driver); // the reason why we wrote this here, it is for us not ot create an obj in LoginPageTest, so we can call it there directly 
		driver.get(prop.getProperty("url"));
}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
//you can use vnc connect - use localhost:49159 (thats IP address in the command line) and containers/nodes will be created. 
//use docker ps -a in comand line to see how many containers you created. then you can acess your test cases from command line or you can do thru eclipse
//so if thru CMDL, you locate them and then run command mvn test and you will see the tests being executed live 