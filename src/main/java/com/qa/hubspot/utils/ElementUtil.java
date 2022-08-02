package com.qa.hubspot.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.hubspot.base.BasePage;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(this.driver);
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator); //create your web element 
		if(BasePage.highlight.equals("true")) { //if its true 
			jsUtil.flash(element); //flash it
		}
		return element;
	}
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator)	}

	public void doSendKeys(By locator, String value) { //if this is not working we have doActions, they are ultimately the same. tahts why we have two methids
		getElement(locator).sendKeys(value);
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions action = new Actions(driver);
		WebElement element = getElement(locator);
		action.sendKeys(element, value).perform();
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doActionsClick(By locator) {
		Actions action = new Actions(driver);
		WebElement element = getElement(locator);
		action.click(element).perform();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	/**
	 * 
	 * @param locator
	 */
	public void getDropDownValues(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("total values in drop down: " + optionsList.size());
		for (int i = 0; i < optionsList.size(); i++) {
			System.out.println(optionsList.get(i).getText());
		}
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 */
	public void selectDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);

	}

	/**
	 * 
	 * @param locator
	 * @param index
	 */
	public void selectDropDown(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 */
	public void selectDropDownValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> valuesList = select.getOptions();
		System.out.println(valuesList.size());
		for (int i = 0; i < valuesList.size(); i++) {
			if (valuesList.get(i).getText().equals(value)) {
				valuesList.get(i).click();
				break;
			}
		}
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public List<String> getDropDownValuesList(By locator) {
		List<String> ar = new ArrayList<String>();
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for (int i = 0; i < optionsList.size(); i++) {
			ar.add(optionsList.get(i).getText());
		}

		return ar;

	}

	/**
	 * this method is used to select the value from the drop down - without
	 * using Select class in Selenium
	 * 
	 * @param locator
	 * @param locatorValue
	 * @param value
	 */
	public void doSelectValueFromDropDownWithOutSelect(String locator, String locatorValue, String value) {
		List<WebElement> valuesList = null;
		if (locator.equals("xpath")) {
			valuesList = driver.findElements(By.xpath(locatorValue));
		} else if (locator.equals("css")) {
			valuesList = driver.findElements(By.cssSelector(locatorValue));
		}
	
		System.out.println(valuesList.size());
	
		for (int i = 0; i < valuesList.size(); i++) {
			if (valuesList.get(i).getText().equals(value)) {
				valuesList.get(i).click();
				break;
			}
		}
	
	// wait custom methods: wait utils:

		public List<WebElement> visiblilityOfAllElements(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(locator)));
			return driver.findElements(locator);
		}

		public WebElement waitForElementToBePresent(String string, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(string));
			return getElement(string);
		}

		public WebElement waitForElementToBeClickable(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return getElement(locator);
		}

		public WebElement waitForElementToBeVisbile(By locator, int timeout) {
			WebElement element = getElement(locator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(element));
			return element;
		}

		public WebElement waitForElementVisibilityLocated(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return getElement(locator);
		}

		public String waitForUrl(String url, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.urlContains(url));
			return driver.getCurrentUrl();
		}

		public boolean waitAlertToBePresent(int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}

		public void clickWhenReady(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			getElement(locator).click();
		}

		public String waitForTitleToBePresent(String title, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.titleContains(title));
			driver.getTitle();
		}
		/*************************** Wait Utils *****************************/

		public Alert waitForAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			return wait.until(ExpectedConditions.alertIsPresent());
		}

		public String getAlertText(int timeOut) {
			String text = waitForAlert(timeOut).getText();
			acceptAlert(timeOut);
			return text;
		}

		public void acceptAlert(int timeOut) {
			waitForAlert(timeOut).accept();
		}

		public void dismissAlert(int timeOut) {
			waitForAlert(timeOut).dismiss();
		}

		public void sendKeysOnAlert(int timeOut, String value) {
			waitForAlert(timeOut).sendKeys(value);
		}

		public String waitForTitleContains(String titleValue, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			if (wait.until(ExpectedConditions.titleContains(titleValue))) {
				return driver.getTitle();
			}
			return null;
		}

		public String waitForTitleIs(String fullTitle, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			if (wait.until(ExpectedConditions.titleIs(fullTitle))) {
				return driver.getTitle();
			}
			return null;
		}
		
		public String waitForUrlContains(String urlFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
			return null;
		}
		
		public String waitForFullUrl(String urlValue, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			if(wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			}
			return null;
		}
		
		
		public void waitForFrameAndSwitchToIt(String frameName, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
		}
		
		public void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		
		public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}

		/**
		 * An expectation for checking that an element is present on the DOM of a page.
		 * This does not necessarily mean that the element is visible.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		/**
		 * 
		 * @param locator
		 * @param timeOut
		 * @param intervalTime
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeOut, int intervalTime) {
			WebDriverWait wait = new WebDriverWait(driver, timeOut, intervalTime);
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		public WebElement waitForElementWithFluentWait(By locator, int timeOut, long pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofMillis(pollingTime))
					.ignoring(NoSuchElementException.class);
			
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		
		public WebDriver waitForFrameWithFluentWait(By locator, int timeOut, long pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofMillis(pollingTime))
					.ignoring(NoSuchElementException.class);
			
			return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		}
		
		public WebElement retryingElement(By locator, int maxAttempts) {
			WebElement element = null;
			int attempts = 0;
			while(attempts < maxAttempts) {
				try {
					element = driver.findElement(locator);
					break;
				}
				catch(Exception e) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println("element is not found in attempt : " + (attempts+1));
				}
				
				
				attempts++;
			}
			return element;
		}

	}



