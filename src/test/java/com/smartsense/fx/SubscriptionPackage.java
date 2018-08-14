package com.smartsense.fx;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SubscriptionPackage {

	private WebDriver driver;
	private WebDriver clientWebDriver;
	private String subscriptionSearch = "trading new 2";
	final static Logger logger = Logger.getLogger(SubscriptionPackage.class);

	@BeforeClass
	public void setUp() throws Exception {
		
		logger.debug("====================init start======================");

		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
		clientWebDriver = new ChromeDriver();
		clientWebDriver.manage().deleteAllCookies();
		clientWebDriver.manage().window().maximize();
		clientWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		clientWebDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		clientWebDriver.get(StringPool.ADMIN_URL);

		driver = new ChromeDriver();
		driver.get(StringPool.CUSTOMER_URL);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		logger.debug("====================init start======================");

	}

	@Test(priority = 0)
	public void testAdminLogin() throws Exception {
		
		logger.debug("testAdminLogin:start");

		// clear the fields first
		clientWebDriver.findElement(By.xpath("//input[@type='text']")).clear();
		clientWebDriver.findElement(By.xpath("//input[@type='password']")).clear();

		// pass the values
		clientWebDriver.findElement(By.xpath("//input[@type='text']")).sendKeys(StringPool.ADMIN_USERNAME);
		clientWebDriver.findElement(By.xpath("//input[@type='password']")).sendKeys(StringPool.ADMIN_PASSWORD);

		Thread.sleep(2000);
		// click on the login button now
		clientWebDriver.findElement(By.xpath("html/body/app-root/div/login/div/div/form/button")).click();

		logger.debug("testAdminLogin:end");
	}

	@Test(dependsOnMethods = { "testAdminLogin" })
	public void testEnableDisableSubscription() throws Exception {
		
		logger.debug("testEnableDisableSubscription:start");
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		// click on the subscription
		clientWebDriver.findElement(By.xpath("//html/body/app-root/app-header/div[2]/ul/li[2]/a")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// search to disable
		searchSubscription(subscriptionSearch);

		// check whether the searched element present
		String name = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[1]"))
				.getText();
		Assert.assertEquals(name, "trading new 2");

		String description = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[2]"))
				.getText();
		Assert.assertEquals(description,
				"Ultimate guide to short term market movements. #11 instruments covered (i) #Expected short term Bias for each instrument with targets and invalidation #Key levels updates everyday #Highlight of the instruments with the highest probability to achieve targets. #Email Notification Only #Updates once a day");

		String duration = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[3]"))
				.getText();
		Assert.assertEquals(duration, "180 Days");

		String planType = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[4]"))
				.getText();
		Assert.assertEquals(planType, "Trading Guide");

		String price = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[5]"))
				.getText();
		Assert.assertEquals(price, "$111.00");

		String discount = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[6]"))
				.getText();
		Assert.assertEquals(discount, "$15");

		String discountedPrice = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div/div[7]"))
				.getText();
		Assert.assertEquals(discountedPrice, "$94.35");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// disable the subscription
		WebElement diableButton = clientWebDriver.findElement(By.xpath(
				"html/body/app-root/div/app-subscription-plan/div/div[2]/div/div[2]/div[1]/div[9]/md-slide-toggle/label/div"));
		diableButton.click();
		Thread.sleep(5000);
		
		logger.debug("testEnableDisableSubscription:end");

	}

	@Test(priority = 1)
	public void testAfterDisableAtCustomer() throws Exception {

		logger.debug("testAfterDisableAtCustomer:start");
		
		// refresh the page
		driver.navigate().refresh();
		Thread.sleep(2000);

		// click on the packages from above menu
		driver.findElement(By.linkText("Packages")).click();
		Thread.sleep(2000);

		// select half yearly duration
		driver.findElement(By.xpath("//*[@id='packages']/div/div[2]/div/a[3]")).click();
		Thread.sleep(2000);

		// check whether the trading guide+half yearly is disabled
		String packageAvailable = driver.findElement(By.xpath("//*[@id='packages']/div/div[4]/div[3]/div/div/h5"))
				.getText();
		Assert.assertEquals(packageAvailable, "This package is currently unavailable");
		Thread.sleep(2000);

		// click on the signup
		driver.findElement(By.xpath("//button[contains(text(),'SIGN UP')]")).click();
		Thread.sleep(2000);

		// select the package subscription and package duration to check whether
		// trading guide half yearly is disabled
		// select subscription
		Function.dropDown(driver, 39, 1, StringPool.MAIN_SHEET);
		Thread.sleep(5000);

		// select duration
		driver.findElement(By
				.xpath("//html/body/app-root/app-register/div/div[1]/div[1]/div[2]/form/div[1]/div/div[10]/div/div/select"))
				.click();
		List<WebElement> duration = driver.findElements(By.tagName("option"));
		for (int i = 0; i < duration.size(); i++) {
			if (!duration.get(i).getText().contains("Half Yearly")) {
				Assert.assertTrue(true);
				System.out.println("true");
				break;
			} else {

				Assert.assertFalse(false);
				System.out.println("false");
			}
		}

		Thread.sleep(5000);

		// signin
		driver.findElement(By.linkText("Sign In")).click();
		Thread.sleep(2000);

		// call function
		Function.loginCredentials(driver, 42);
		Thread.sleep(2000);

		// click on the user profile
		driver.findElement(By.className("user-profile")).click();
		Thread.sleep(3000);

		// click on the profile option
		driver.findElement(By.linkText("Profile")).click();
		Thread.sleep(5000);

		// click on the add new plan
		driver.findElement(By.xpath("//button[contains(text(),'Add new plan')]")).click();
		Thread.sleep(2000);

		// pop up window
		WebElement popup = driver.findElement(By.xpath("//md-select[@placeholder='Subscription Package']"));
		popup.click();
		Actions action = new Actions(driver);
		WebElement clickOnElement = driver.findElement(By.xpath("//md-option[contains(text(),'Trading Guide')]"));
		action.moveToElement(clickOnElement).click().build().perform();

		Thread.sleep(Utils.MAX_WAIT_TIME);

		driver.findElement(By.xpath("//md-select[@placeholder='Subscription Duration']")).click();
		List<WebElement> durationNew = driver.findElements(By.tagName("md-option"));
		for (int i = 0; i < durationNew.size(); i++) {
			if (!durationNew.get(i).getText().contains("Half Yearly")) {
				Assert.assertTrue(true);
				System.out.println("true");
				break;
			} else {

				Assert.assertFalse(false);
				System.out.println("false");
			}
		}
		Thread.sleep(3000);

		// select monthly for canceling the pop up
		WebElement clickOnElementNew = driver.findElement(By.xpath("//md-option[contains(text(),'Monthly')]"));
		action.moveToElement(clickOnElementNew).click().build().perform();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the cancel button
		driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
		Thread.sleep(2000);

		// click on the home page
		driver.findElement(
				By.xpath("//html/body/app-root/app-profile/app-header/div/div[1]/div[1]/div[1]/div[1]/a/img[2]"))
				.click();
		Thread.sleep(2000);

		// click on the packages
		driver.findElement(By.linkText("Packages")).click();
		Thread.sleep(2000);

		// select half yearly duration
		driver.findElement(By.xpath("//*[@id='packages']/div/div[2]/div/a[3]")).click();
		Thread.sleep(2000);

		// check whether the trading guide+half yearly is disabled
		String packageAvailableCheck = driver.findElement(By.xpath("//*[@id='packages']/div/div[4]/div[3]/div/div/h5"))
				.getText();
		Assert.assertEquals(packageAvailableCheck, "This package is currently unavailable");
		Thread.sleep(2000);

		logger.debug("testAfterDisableAtCustomer:end");
	}

	private void searchSubscription(String subscriptionSearch) throws Exception {

		WebElement search = clientWebDriver.findElement(By.xpath("//input[@placeholder='Sell, Euro...']"));
		search.clear();
		search.sendKeys(subscriptionSearch);
		Thread.sleep(5000);

	}

	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
		clientWebDriver.close();
		clientWebDriver.quit();
	}

}
