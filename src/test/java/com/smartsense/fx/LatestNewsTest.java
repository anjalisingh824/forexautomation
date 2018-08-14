package com.smartsense.fx;
/**
 * Admin login
 * add latest news
 * check for the mandatory errors 
 * check on the customer side whether latest news added or not?
 * now disable latest news on the admin side
 * check on the customer side whether the disabled news is enabled or disabled
 * now enable the latest news on the admin side 
 * check whether the news is now visible after enabling
 * now delete the latest news on the admin side which was added
 * now check whether the deleted news is visible on the customer side or not?
 */

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LatestNewsTest {
	private WebDriver driver;
	private WebDriver clientWebDriver;
	final static Logger logger = Logger.getLogger(LatestNewsTest.class);
	private String quation = StringPool.AUTOMATION_PREFIX + System.currentTimeMillis() + "_LatestNews";

	@BeforeClass
	public void setUp() throws Exception {
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
		driver = new ChromeDriver();
		driver.get(StringPool.CUSTOMER_URL);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		clientWebDriver = new ChromeDriver();
		clientWebDriver.manage().deleteAllCookies();
		clientWebDriver.manage().window().maximize();
		clientWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		clientWebDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		clientWebDriver.get(StringPool.ADMIN_URL);
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
	public void addLatestNews() throws Exception {
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the Latest News
		clientWebDriver.findElement(By.linkText("Latest News")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the add button
		clientWebDriver.findElement(By.xpath("//a[@title='Add']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// check for the errors
		clientWebDriver.findElement(By.xpath("//textarea[@placeholder='News']")).sendKeys(Keys.TAB);
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// error
		String mandatoryLatestNewsError = clientWebDriver
				.findElement(By
						.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/form/div/div[1]/div[1]/validation/small/span"))
				.getText();
		Assert.assertEquals(mandatoryLatestNewsError, StringPool.LATEST_NEWS_ERROR);

		Thread.sleep(Utils.MIN_WAIT_TIME);

		// pass one character(minimum length)
		clientWebDriver.findElement(By.xpath("//textarea[@placeholder='News']")).sendKeys("a");
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// error
		String getLengthError = clientWebDriver
				.findElement(By
						.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/form/div/div[1]/div[1]/validation/small/span"))
				.getText();
		Assert.assertEquals(getLengthError, StringPool.LATEST_NEWS_LENGTH_ERROR);

		// clear the field
		clientWebDriver.findElement(By.xpath("//textarea[@placeholder='News']")).clear();

		// maximum length
		clientWebDriver.findElement(By.xpath("//textarea[@placeholder='News']")).sendKeys(
				"This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews This is LatestNews");
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// error
		String getLengthMaxError = clientWebDriver
				.findElement(By
						.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/form/div/div[1]/div[1]/validation/small/span"))
				.getText();
		Assert.assertEquals(getLengthMaxError, StringPool.LATEST_NEWS_LENGTH_ERROR);

		// refresh the page
		clientWebDriver.navigate().refresh();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the add button
		clientWebDriver.findElement(By.xpath("//a[@title='Add']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// pass value
		clientWebDriver.findElement(By.xpath("//textarea[@placeholder='News']")).sendKeys(quation);
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// click on the save button
		clientWebDriver.findElement(By.xpath("//button[@type='submit']")).click();

		// search what we have added
		searchLatestNews(quation);

		// news
		String news = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/div/div[2]/div/div[1]"))
				.getText();
		Assert.assertEquals(news, quation);
		Thread.sleep(1000);

		// date check calling function
		String dateAndTime = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/div/div[2]/div/div[2]"))
				.getText();
		System.out.println(dateAndTime);
		// Assert.assertEquals(clientWebDriver.getPageSource().contains(dateFormat()),
		// true);

	}

	@Test(dependsOnMethods = { "addLatestNews" })
	public void testCustomerSideForLatestestNews() throws Exception {
		logger.debug("testCustomerSideForLatestestNews:start");

		// refresh the page
		driver.navigate().refresh();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		Assert.assertEquals(driver.getPageSource().contains(quation), true);
		Thread.sleep(Utils.MIN_WAIT_TIME);

		logger.debug("testCustomerSideForLatestestNews:end");

	}

	@Test(dependsOnMethods = { "addLatestNews" })
	public void testDisableLatestNews() throws Exception {

		logger.debug("testDisableLatestNews:Start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// search again
		searchLatestNews(quation);

		// click to disable
		clientWebDriver
				.findElement(By
						.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/div/div[2]/div[1]/div[5]/md-slide-toggle/label/div/div/div[1]"))
				.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("testDisableLatestNews:end");

	}

	@Test(dependsOnMethods = { "testDisableLatestNews" })
	public void testAfterDisableInCustomerSide() throws Exception {

		logger.debug("testAfterDisableInCustomerSide:start");

		// refresh the page
		driver.navigate().refresh();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		Assert.assertEquals(driver.getPageSource().contains(quation), false);
		logger.debug("testAfterDisableInCustomerSide:end");

	}

	@Test(dependsOnMethods = { "testDisableLatestNews" })
	public void testEnableLatestNews() throws Exception {
		logger.debug("testEnableLatestNews:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// search again
		searchLatestNews(quation);

		// click to enable
		clientWebDriver
				.findElement(By
						.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/div/div[2]/div[1]/div[5]/md-slide-toggle/label/div/div/div[1]"))
				.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("testEnableLatestNews:end");

	}

	@Test(dependsOnMethods = { "testEnableLatestNews" })
	public void testAfterEnableCustomerSide() throws Exception {
		logger.debug("testAfterEnableCustomerSide:start");

		// refresh the page
		driver.navigate().refresh();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		Assert.assertEquals(driver.getPageSource().contains(quation), true);
		Thread.sleep(Utils.MIN_WAIT_TIME);

		logger.debug("testAfterEnableCustomerSide:end");
	}

	@Test(dependsOnMethods = { "testAfterEnableCustomerSide" })
	public void deleteLatestNewsAdminSide() throws Exception {
		logger.debug("deleteLatestNewsAdminSide:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// search
		searchLatestNews(quation);

		// click on the delete icon
		clientWebDriver
				.findElement(
						By.xpath("//html/body/app-root/div/app-latest-news/div/div[2]/div/div[2]/div[1]/div[4]/a/i"))
				.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// delete now
		WebElement deleteLatestNews = clientWebDriver.findElement(By.xpath("//button[contains(text(),'YES')]"));
		deleteLatestNews.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// search again
		searchLatestNews(quation);
		Thread.sleep(Utils.MIN_WAIT_TIME);

		String checkNoNews = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-latest-news/div/no-data/div/h2")).getText();
		Assert.assertEquals(checkNoNews, "No News Found.");
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("deleteLatestNewsAdminSide:end");
	}

	@Test(dependsOnMethods = { "deleteLatestNewsAdminSide" })
	public void afterDeleteLatestNewsCustomerSide() throws Exception {
		logger.debug("afterDeleteLatestNewsCustomerSide:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// refresh the page
		driver.navigate().refresh();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		Assert.assertEquals(driver.getPageSource().contains(quation), false);

		logger.debug("afterDeleteLatestNewsCustomerSide:end");
	}

	private void searchLatestNews(String quation) throws Exception {
		WebElement search = clientWebDriver.findElement(By.xpath("//input[@placeholder='Search...']"));
		search.clear();
		search.sendKeys(quation);
		Thread.sleep(Utils.MAX_WAIT_TIME);
	}

	// private String dateFormat() {
	// DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy, h:mm a");
	// Calendar calender = Calendar.getInstance();
	// String todayDate = dateFormat.format(calender.getTime());
	// System.out.println(todayDate);
	// return todayDate;
	//
	// }

	@AfterClass()
	public void tearDown() {
		driver.close();
		driver.quit();
		clientWebDriver.close();
		clientWebDriver.quit();
	}

}
