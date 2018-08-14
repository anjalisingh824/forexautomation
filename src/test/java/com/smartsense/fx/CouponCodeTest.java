package com.smartsense.fx;

/**
 * Admin login
 * add coupon code
 * search the added coupon code
 * add people in the added coupon code
 *customer side check for coupon code
 *sign up 
 *after login---add new package
 *renew package
 *again home page
 *click on the packages
 *and the check for the coupon code by clicking on the subscribe button
 */

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CouponCodeTest {

	private String storeValue;
	private WebDriver driver;
	public static String link = null;
	public static String get;
	private WebDriver clientWebDriver;
	private String campaignName = "Summer discount";
	final static Logger logger = Logger.getLogger(CouponCodeTest.class);

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

		logger.debug("testAdminLogin : start");
		// clear the fields first
		clientWebDriver.findElement(By.xpath("//input[@type='text']")).clear();
		clientWebDriver.findElement(By.xpath("//input[@type='password']")).clear();

		// pass the values
		clientWebDriver.findElement(By.xpath("//input[@type='text']")).sendKeys(StringPool.ADMIN_USERNAME);
		clientWebDriver.findElement(By.xpath("//input[@type='password']")).sendKeys(StringPool.ADMIN_PASSWORD);

		Thread.sleep(2000);
		// click on the login button now
		clientWebDriver.findElement(By.xpath("html/body/app-root/div/login/div/div/form/button")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		logger.debug("testAdminLogin : end");
	}

	@Test(dependsOnMethods = { "testAdminLogin" })
	public void addCouponCodeErrors() throws Exception {
		logger.debug("addCouponCodeErrors : start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the coupon code
		WebElement clickOnCouponCode = clientWebDriver.findElement(By.linkText("Coupon code"));
		Thread.sleep(7000);
		clickOnCouponCode.click();

		// click on the add button to click on the coupon code
		WebElement addButton = clientWebDriver.findElement(By.xpath("//a[@title='Add']"));
		addButton.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// use tab now to check for the mandatory errors
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Campaign Name']")).sendKeys(Keys.TAB);

		clientWebDriver.findElement(By.xpath("//md-select[@placeholder='Plan type']")).sendKeys(Keys.TAB);

		clientWebDriver.findElement(By.xpath("//md-select[@placeholder='Duration']")).sendKeys(Keys.TAB);

		clientWebDriver.findElement(By.xpath("//input[@placeholder='From date']")).sendKeys(Keys.TAB);

		clientWebDriver.findElement(By.xpath("//input[@placeholder='To date']")).sendKeys(Keys.TAB);

		clientWebDriver.findElement(By.xpath("//input[@placeholder='Discount(%)']")).sendKeys(Keys.TAB);

		Thread.sleep(2000);

		// assert now whether error were present on the web page
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.CAMPAIGN_NAME_MAN_ERR0R), true);

		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.PLAN_TYPE_MAN_ERROR), true);

		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.DURATION_MAN_ERROR), true);

		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.FROM_DATE_MAN_ERROR), true);

		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.TO_DATE_MAN_ERROR), true);

		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.DISCOUNT_MAN_ERROR), true);

		Thread.sleep(2000);

		// campaign name(passing 'a' for checking the minimum number of
		// characters allowed)
		campaignNameErrorCheck("a");
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.CAMPAIGN_LENGTH_ERROR), true);

		// discount (passing 'a' as value)
		discountErrorCheck("a");
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.DISCOUNT_VALID_ERROR), true);

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// campaign name(passing 'a' for checking the maximum number of
		// characters allowed)
		campaignNameErrorCheck("CampaignNameCampaignNameCampaignNameCampaignNameCampaign");
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.CAMPAIGN_LENGTH_ERROR), true);

		// discount (trying to pass more than 2 digits,but allowed 2 digits only
		// )
		discountErrorCheck("100");

		Thread.sleep(5000);
		// campaign name (pass special characters)
		campaignNameErrorCheck("Monsoon@#$%^%$#");
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.CAMPAIGN_VALID_ERROR_MESSAGE), true);

		// discount(pass special characters)
		discountErrorCheck("12!!@$##$%$#");
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.DISCOUNT_VALID_ERROR), true);

		logger.debug("addCouponCodeErrors : end");
	}

	@Test(dependsOnMethods = { "addCouponCodeErrors" })
	public void addCouponCode() throws Exception {

		logger.debug("addCouponCode : start");

		// refresh the page
		clientWebDriver.navigate().refresh();
		Thread.sleep(5000);

		// click on the add button to click on the coupon code
		clientWebDriver.findElement(By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[1]/a/i")).click();
		Thread.sleep(5000);

		// campaign name
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Campaign Name']")).sendKeys("Summer discount");

		// plan type
		Actions action = new Actions(clientWebDriver);

		WebElement planType = clientWebDriver.findElement(By.xpath("//md-select[@placeholder='Plan type']"));
		planType.click();
		Thread.sleep(5000);
		WebElement clickPlanType = clientWebDriver.findElement(By.xpath("//md-option[contains(text(),'Signal')]"));
		action.moveToElement(clickPlanType).click().build().perform();
		Thread.sleep(5000);

		// duration
		WebElement duration = clientWebDriver.findElement(By.xpath("//md-select[@placeholder='Duration']"));
		duration.click();
		Thread.sleep(5000);
		WebElement clickDuration = clientWebDriver.findElement(By.xpath("//md-option[contains(text(),' Quarterly')]"));
		action.moveToElement(clickDuration).click().build().perform();
		Thread.sleep(5000);

		// click on the date picker button
		clientWebDriver.findElement(By.className("mat-datepicker-toggle")).click();
		Thread.sleep(5000);

		// from date
		WebElement datePicker = clientWebDriver.findElement(By.className("mat-calendar"));
		action.moveToElement(datePicker).build().perform();

		List<WebElement> dateSelect = clientWebDriver.findElements(By.tagName("td"));
		for (int i = 0; i < dateSelect.size(); i++) {
			if (dateSelect.get(i).getText().contains("23")) {
				logger.info("Entering into if loop");
				WebElement elementClick = dateSelect.get(i);
				Thread.sleep(5000);
				elementClick.click();
				break;
			}

		}
		Thread.sleep(5000);

		// to date
		clientWebDriver.findElement(By.xpath("//input[@placeholder='To date']")).click();
		Thread.sleep(3000);

		// click on the next button to select the next month date
		clientWebDriver.findElement(By.xpath("//*[@id='md-datepicker-1']/div[1]/div/button[3]")).click();
		Thread.sleep(5000);

		// to date
		WebElement datePickerSelect = clientWebDriver.findElement(By.className("mat-calendar"));
		action.moveToElement(datePickerSelect).build().perform();

		List<WebElement> dateSelectTo = clientWebDriver.findElements(By.tagName("td"));
		for (int i = 0; i < dateSelectTo.size(); i++) {
			if (dateSelectTo.get(i).getText().contains("30")) {
				logger.info("Entering into if loop");
				WebElement elementClick = dateSelectTo.get(i);
				Thread.sleep(5000);
				elementClick.click();
				break;
			}

		}
		Thread.sleep(5000);

		// discount
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Discount(%)']")).sendKeys("5");
		Thread.sleep(5000);

		// click on the save button
		clientWebDriver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(5000);

		// search the coupon code that we have added
		searchCoupanCode(campaignName);

		// check for the values entered
		String campaignName = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div/div[1]"))
				.getText();
		Assert.assertEquals(campaignName, "Summer discount");

		String planTypeCheck = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div[1]/div[2]"))
				.getText();
		Assert.assertEquals(planTypeCheck, "Signal");

		String durationCheck = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div[1]/div[3]"))
				.getText();
		Assert.assertEquals(durationCheck, "90 Days");

		String validity = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div[1]/div[4]"))
				.getText();
		Assert.assertEquals(validity, "23/08/2017 - 30/09/2017");

		String discount = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div[1]/div[5]"))
				.getText();
		Assert.assertEquals(discount, "5 %");

		Thread.sleep(5000);

		logger.debug("addCouponCode : end");
	}

	@Test(dependsOnMethods = { "addCouponCode" })
	public void addPeopleCouponCode() throws Exception {

		logger.debug("addPeopleCouponCode : start");
		Thread.sleep(5000);

		// click on the add people
		clientWebDriver.findElement(By.xpath("//button[contains(text(),'Add People')]")).click();
		Thread.sleep(5000);

		// first select invalid files to check whether it shows error or not
		clientWebDriver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys("/home/smart/Downloads/Core Defense Mechanisms against security attacks on Web (1).pptx");
		Thread.sleep(1000);
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.INVALID_IMAGES_ERROR), true);
		Thread.sleep(5000);

		// taking pdf for file upload
		clientWebDriver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys("/home/smart/Downloads/ManualTesting.pdf");
		Thread.sleep(1000);
		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.INVALID_IMAGES_ERROR), true);
		Thread.sleep(5000);

		// Now passing xls file but the file containing special
		// characters,digits etc and only 2 correct email ids
		clientWebDriver.findElement(By.xpath("//input[@type='file']")).sendKeys("/home/smart/Downloads/emails.xls");
		Thread.sleep(2000);

		// click on the save button
		clientWebDriver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);

		Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.SUCCESS_CAMPAIGN_ADD_MESSGAE), true);
		Thread.sleep(5000);

		// now check by passing csv file
		clientWebDriver.findElement(By.xpath("//input[@type='file']")).sendKeys("/home/smart/Documents/email.csv");
		Thread.sleep(2000);

		// click on the save button
		clientWebDriver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);

		// Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.SUCCESS_CAMPAIGN_ADD_MESSGAE),
		// true);
		// Thread.sleep(5000);

		// select the file containing email addresses to upload
		clientWebDriver.findElement(By.xpath("//input[@type='file']")).sendKeys("/home/smart/Downloads/emails.xlsx");
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the save button
		clientWebDriver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(5000);

		// Assert.assertEquals(clientWebDriver.getPageSource().contains(StringPool.SUCCESS_CAMPAIGN_ADD_MESSGAE),
		// true);
		// Thread.sleep(5000);

		// getting the coupon code value;
		getCouponCodeValue();

		Thread.sleep(5000);

		// now click on the cancel button
		clientWebDriver.findElement(By.xpath("//button[contains(text(),'CANCEL')]")).click();
		Thread.sleep(5000);

		logger.debug("addPeopleCouponCode : end");
	}

	private String getCouponCodeValue() {
		// get the first coupon code value
		storeValue = clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[3]/div[1]/div[2]/div[1]/div[2]"))
				.getText();
		System.out.println(storeValue);
		return storeValue;

	}

	@Test(dependsOnMethods = { "addPeopleCouponCode" })
	public void readEmail() throws Exception {

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect(StringPool.CONNECTION_EMAIL, StringPool.USERNAME, StringPool.PASSWORD);
			Folder inbox = store.getFolder("INBOX");
			Thread.sleep(5000);
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());
			Address[] in = msg.getFrom();
			for (Address address : in) {
				System.out.println("FROM:" + address.toString());
			}

			System.out.println("SENT DATE:" + msg.getSentDate());
			System.out.println("SUBJECT:" + msg.getSubject());
			System.out.println("CONTENT:" + msg.getContent());

		}

		catch (Exception mex) {
			mex.printStackTrace();

		}

	}

	@Test(dependsOnMethods = { "addPeopleCouponCode" })
	public void testCustomerForCouponCode() throws Exception {

		logger.debug("testCustomerForCouponCode:start");
		Thread.sleep(5000);

		// click on the signup button
		driver.findElement(By.xpath("//button[contains(text(),'SIGN UP')]")).click();
		Thread.sleep(2000);

		// select the plan
		Function.dropDown(driver, 43, 1, StringPool.MAIN_SHEET);
		Thread.sleep(5000);

		// select the duration
		Function.dropDown(driver, 44, 1, StringPool.MAIN_SHEET);
		Thread.sleep(5000);

		// getCouponCodeValue();
		// pass the coupon code value
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(storeValue);

		// click on the tab to check whether the coupon code has been verified
		// or not
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		Assert.assertEquals(driver.getPageSource().contains(StringPool.COUPON_CODE_SUCCESS_MESSAGE), true);
		Thread.sleep(5000);

		// click on the sign In button
		driver.findElement(By.linkText("Sign In")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		logger.debug("testCustomerForCouponCode:end");

	}

	@Test(dependsOnMethods = { "testCustomerForCouponCode" })
	public void testAfterLoginForCouponCode() throws Exception {
		logger.debug("testAfterLoginForCouponCode:start");

		Thread.sleep(Utils.MIN_WAIT_TIME);

		// calling function for doing login
		Function.loginCredentials(driver, 42);

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
		// plan type
		WebElement popup = driver.findElement(By.xpath("//md-select[@placeholder='Subscription Package']"));
		popup.click();
		Actions action = new Actions(driver);
		WebElement clickOnElement = driver.findElement(By.xpath("//md-option[contains(text(),'Signal')]"));
		action.moveToElement(clickOnElement).click().build().perform();

		Thread.sleep(5000);

		// duration
		WebElement popupSecond = driver.findElement(By.xpath("//md-select[@placeholder='Subscription Duration']"));
		popupSecond.click();
		WebElement clickElement = driver.findElement(By.xpath("//md-option[contains(text(),'Quarterly')]"));
		Thread.sleep(Utils.MAX_WAIT_TIME);
		action.moveToElement(clickElement).click().build().perform();

		Thread.sleep(5000);

		// click on the checkbox for coupon code
		WebElement clickOnCheckbox = driver.findElement(By.xpath("//span[contains(text(),'Do you have a coupon code?')]"));
		clickOnCheckbox.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// pass the value for coupan code
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(storeValue);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the tab to check whether the coupan code is valid or not
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(Keys.TAB);
		Thread.sleep(3000);
		Assert.assertEquals(driver.getPageSource().contains(StringPool.COUPON_CODE_SUCCESS_MESSAGE), true);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the cancel button
		driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the renew package now
		driver.findElement(By.xpath("//html/body/app-root/app-profile/div/div/div/div[1]/div[2]/div[6]/div[2]/div/table/tbody/tr[2]/td[9]/button")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the checkbox for coupon code
		WebElement clickOnCheckboxForRenew =driver.findElement(By.xpath("//span[contains(text(),'Do you have a coupon code?')]"));
		clickOnCheckboxForRenew.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// pass the value for coupan code
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(storeValue);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the tab to check whether the coupan code is valid or not
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(Keys.TAB);
		Thread.sleep(3000);
		Assert.assertEquals(driver.getPageSource().contains(StringPool.COUPON_CODE_SUCCESS_MESSAGE), true);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the cancel button
		driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the home page
		WebElement homePageClick = driver.findElement(
				By.xpath("//html/body/app-root/app-profile/app-header/div/div[1]/div[1]/div[1]/div[1]/a/img[2]"));
		homePageClick.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// go to the packages
		driver.findElement(By.linkText("Packages")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the quarterly
		driver.findElement(By.xpath("//*[@id='packages']/div/div[2]/div/a[2]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the subscribe button
		driver.findElement(By.xpath("//*[@id='packages']/div/div[4]/div[1]/div/div/button")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);


		// click on the checkbox for coupon code
		WebElement clickOnCheckboxNew = driver.findElement(By.xpath("//span[contains(text(),'Do you have a coupon code?')]"));
		clickOnCheckboxNew.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// pass the value for coupan code
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(storeValue);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the tab to check whether the coupan code is valid or not
		driver.findElement(By.xpath("//input[@placeholder='Coupon code']")).sendKeys(Keys.TAB);
		Thread.sleep(3000);
		Assert.assertEquals(driver.getPageSource().contains(StringPool.COUPON_CODE_SUCCESS_MESSAGE), true);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the cancel button
		driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		logger.debug("testAfterLoginForCouponCode:end");

	}

	// @Test(dependsOnMethods = { "addPeopleCouponCode" })
	// public void editCouponCode() throws Exception {
	//
	// logger.debug("editCouponCode : Start");
	// Thread.sleep(2000);
	//
	// // now click on the edit button
	// clientWebDriver
	// .findElement(By
	// .xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div[1]/div[8]/a/i"))
	// .click();
	// Thread.sleep(5000);
	//
	// // campaign name
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='Campaign
	// Name']")).clear();
	// Thread.sleep(1000);
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='Campaign
	// Name']")).sendKeys("Summer sale");
	//
	// // plan type
	// Actions action = new Actions(clientWebDriver);
	//
	// WebElement planType =
	// clientWebDriver.findElement(By.xpath("//md-select[@placeholder='Plan
	// type']"));
	// planType.click();
	// Thread.sleep(5000);
	// WebElement clickPlanType = clientWebDriver
	// .findElement(By.xpath("//md-option[contains(text(),'Trading Guide')]"));
	// action.moveToElement(clickPlanType).click().build().perform();
	// Thread.sleep(5000);
	//
	// // duration
	// WebElement duration =
	// clientWebDriver.findElement(By.xpath("//md-select[@placeholder='Duration']"));
	// duration.click();
	// Thread.sleep(5000);
	// WebElement clickDuration = clientWebDriver
	// .findElement(By.xpath("//md-option[contains(text(),' Half Yearly')]"));
	// action.moveToElement(clickDuration).click().build().perform();
	// Thread.sleep(5000);
	//
	// // click on the date picker button
	// clientWebDriver.findElement(By.className("mat-datepicker-toggle")).click();
	// Thread.sleep(5000);
	//
	// // from date
	// WebElement datePicker =
	// clientWebDriver.findElement(By.className("mat-calendar"));
	// action.moveToElement(datePicker).build().perform();
	//
	// List<WebElement> dateSelect =
	// clientWebDriver.findElements(By.tagName("td"));
	// for (int i = 0; i < dateSelect.size(); i++) {
	// if (dateSelect.get(i).getText().contains("19")) {
	// logger.info("Entering into if loop");
	// WebElement elementClick = dateSelect.get(i);
	// Thread.sleep(5000);
	// elementClick.click();
	// break;
	// }
	//
	// }
	// Thread.sleep(5000);
	//
	// // to date
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='To
	// date']")).click();
	// Thread.sleep(5000);
	//
	// // to date
	// WebElement datePickerSelect =
	// clientWebDriver.findElement(By.className("mat-calendar"));
	// action.moveToElement(datePickerSelect).build().perform();
	//
	// List<WebElement> dateSelectTo =
	// clientWebDriver.findElements(By.tagName("td"));
	// for (int i = 0; i < dateSelectTo.size(); i++) {
	// if (dateSelectTo.get(i).getText().contains("29")) {
	// logger.info("Entering into if loop");
	// WebElement elementClick = dateSelectTo.get(i);
	// Thread.sleep(5000);
	// elementClick.click();
	// break;
	// }
	//
	// }
	// Thread.sleep(5000);
	//
	// // discount
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='Discount(%)']")).clear();
	// Thread.sleep(1000);
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='Discount(%)']")).sendKeys("3");
	// Thread.sleep(5000);
	//
	// // click on the save button
	// clientWebDriver.findElement(By.xpath("//button[@type='submit']")).click();
	// Thread.sleep(5000);
	//
	// // refresh the page again to check whether the coupon code has been
	// // edited
	// clientWebDriver.navigate().refresh();
	// Thread.sleep(5000);
	//
	// logger.debug("editCouponCode : end");
	// }
	//
	// @Test(dependsOnMethods = { "editCouponCode" })
	// public void deleteCouponCode() throws Exception {
	//
	// logger.debug("deleteCouponCode : Start");
	// Thread.sleep(5000);
	// // search the coupon code after editing
	// WebElement search =
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='Search...']"));
	// search.sendKeys("Summer sale");
	// Thread.sleep(2000);
	//
	// // Now click on the delete button
	// clientWebDriver
	// .findElement(
	// By.xpath("//html/body/app-root/div/app-coupan-code/div/div/div[2]/div/div[2]/div/div[7]/a/i"))
	// .click();
	// Thread.sleep(5000);
	//
	// // click on the yes button
	// WebElement confirm = clientWebDriver
	// .findElement(By.xpath("//html/body/app-root/div/app-coupan-code/div[2]/div/div/button[1]"));
	// confirm.click();
	// Thread.sleep(5000);
	//
	// // search the coupon code after editing
	// WebElement searchAgain =
	// clientWebDriver.findElement(By.xpath("//input[@placeholder='Search...']"));
	// searchAgain.clear();
	// searchAgain.sendKeys("Summer sale");
	// Thread.sleep(5000);
	//
	// // record should not be present ,therefore search again
	// String NoCouponCode = clientWebDriver
	// .findElement(By.xpath("//html/body/app-root/div/app-coupan-code/div/div/no-data/div/h2")).getText();
	// Assert.assertEquals(NoCouponCode, "No Coupon Code Found.");
	// Thread.sleep(5000);
	// logger.debug("deleteCouponCode : end");
	//
	// }

	private void searchCoupanCode(String campaignName) throws Exception {

		WebElement search = clientWebDriver.findElement(By.xpath("//input[@placeholder='Search...']"));
		search.clear();
		search.sendKeys(campaignName);
		Thread.sleep(5000);

	}

	private void discountErrorCheck(String value) throws Exception {
		WebElement discountErrorCheck = clientWebDriver.findElement(By.xpath("//input[@placeholder='Discount(%)']"));
		discountErrorCheck.clear();
		discountErrorCheck.sendKeys(value);
		discountErrorCheck.sendKeys(Keys.TAB);
		Thread.sleep(2000);

	}

	private void campaignNameErrorCheck(String value) throws Exception {
		WebElement campaignNameError = clientWebDriver.findElement(By.xpath("//input[@placeholder='Campaign Name']"));
		campaignNameError.clear();
		campaignNameError.sendKeys(value);
		campaignNameError.sendKeys(Keys.TAB);
		Thread.sleep(2000);

	}

	@AfterClass
	public void tearDown() {
		clientWebDriver.close();
		clientWebDriver.quit();
		driver.close();
		driver.quit();
	}
}
