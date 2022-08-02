package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.hubspot.utils.OptionsManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/*
	 *This method is used to initialize the browser on the bases of given browser 
	 *@param browser
	 *@return This return webdriver driver 
	 */
	
	
	public WebDriver init_driver(String browser, String browserVersion) { //browserVersion only in case of initial remote web driver
		System.out.println("browser value is:" + browser);

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		
		if(browser.equalsIgnoreCase("chrome")) { //ignore case if someone gave a space when writing "chrome" in xml 
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver();		
			if(Boolean.parseBoolean(prop.getProperty("remote"))) { //conversion
				init_remoteDriver("chrome", browserVersion);
			}
			else {
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		
		else if(browser.equalsIgnoreCase("firefox")) { //ignore case if someone gave a space when writing "chrome" in xml 
			WebDriverManager.firefoxdriver().setup();
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) { //conversion
				init_remoteDriver("firefox", browserVersion);
			}else {
			}
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions())); 
					
		}
		else if (browser.equalsIgnoreCase("safari")) { //igore case if someone gave a space when writing "chrome" in xml 
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver()); 
		}
		else {
			System.out.println("please pass the correct browser valeu: " + browser);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();		
		
	}
	
	private void init_remoteDriver(String browser, String browserVersion) {
		
		System.out.println("running test on remote grid: ' + browser");

		if(browser.equals("chrome")) {
			
			DesiredCapabilities cap = DesiredCapabilities.chrome(); //object of desired capabilities 
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
//			cap.setCapability("screenWith", "1920"); // these are some properties form zalenium 
//			cap.setCapability("screenHeight", "1920");
			cap.setCapability("browserVersion", browserVersion);
			cap.setCapability("browserName",  "chrome");
			cap.setCapability("enableVNC", true);
			
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else if (browser.equals("firefox")) {
				DesiredCapabilities cap = DesiredCapabilities.firefox(); //object of desired capabilities 
				cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
				cap.setCapability("browserVersion", browserVersion);
				cap.setCapability("enableVNC", true);
				
				try {
					tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
				}catch(MalformedURLException e) {
					e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * getDriver using ThreadLocal
	 */
	
	public static synchronized WebDriver getDriver() { //synchronized - at the time it will pick only one test 
		return tlDriver.get(); //whatever the drive is avaialble, you just give it to me. 
	}
	
	/*This method is used to load the properties from config.properties fi;e 
	 * 
	 * @return it return Properties prop reference 
	 */
	public Properties init_prop() { //we have to create the object of properties file here 
		
		prop = new Properties(); //here comes loading part: 
		try {
			FileInputStream ip = new FileInputStream("./src/main/java/com/qa/hubspot/config/config.properties"); //FileInputStream is used to make connection to this path 
			prop.load(ip); // this will load the properties
		} catch (FileNotFoundException e) { //if file will not be found. We always use try-catch here not throws 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	/**
	 * This method is used to take a screenshot 
	 * it will return the path of screenshot
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE); //getScreenshotAs is the method used to take screen shots
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";  //this is the path where screenshots are save, (user directory - screenshot folder -attach with .png / jpg file.)
		File destination = new File(path); //this is src copying the oath and puting in destination file 
		try {
			FileUtils.copyFile(src, destination);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return path; 
		
	} //the problem here is that screenshot driver is null. So how do we fix it? 
	//extend report and parallel execution are not working properly and the reason is the driver.
	//When you have lets say 5 test cases, one thread will takeone driver in the test and work on in, and at the same time the other thread wil take the driver and 
	//try to work on it but the dirver is still busy with the first thread. (And because its runing in the parallel execution) 
	//so we need to use thread local. It Create local copies of the web driver for the same driver. Drivers will have same sessions id but it will make copies of the same driver. 
		
	//to stop the container thru CMDL, just write command: docker stop (and container ID ex: 6ac2b848dad8). and to restart you do: docker restart (and container ID ex: 6ac2b848dad8)
	//to reemove a container, first stop the docker, press enterm and next comand is: docker rm (and container ID ex: 6ac2b848dad8)
	//rmi - remove the images . docked rmi (and container ID ex: 6ac2b848dad8)
	//docker system prune -a - will remove everything form the system 
	//but we have docker composer provided by selenium. All of the images will be in containers they relate to. LIke chromes will be with chromes and FF will be with FF.
	//but in other docker grid, each image has its own composer so docker hyper kit (docker composer) is better but taking lots of resoucrces, computer has to be 8GB min
	//-d is detach mode means dont show me the logs and donts stop the test
	//so to use the docker composer you type in the CMDL docker-compose up 
	//docker-compose down is closing it. 
	//docker-compose scale chrome=4     this will increase the amount of conrtainers you want. BUT if you want 4 it will include the existing one too which will only create 3 more cua one is there 
	//selenoid runs on 8080 - 
	//.yml needs 2 services- (will create one selenoid UI also.) 1. selenium grid 2. selenoid ui - will autmoaically download the image and create the container. 
	//docker system prune -a - will delete all images and contaoners taht are STOPPED. not the ones runnign 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
}
