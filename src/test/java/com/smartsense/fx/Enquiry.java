package com.smartsense.fx;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Enquiry {

	private WebDriver driver;
	private WebDriver clientWebDriver;
	private String email = "anjali.singh12@gmail.com";
	final static Logger logger = Logger.getLogger(Enquiry.class);


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

	@Test
	public void testEnquiry() throws Exception {

		logger.debug("testEnquiry:start");
		// passing values from excel sheet
		int row = 36;

		// pass all the values in the contact us page,click on the menu bar
		driver.findElement(By.xpath("//*[@id='home']/nav/div[1]/ul[2]/li[8]/a/img")).click();

		// name
		driver.findElement(By.xpath("//input[@placeholder='Name']"))
				.sendKeys(ExcelUtils.getCellData(row, 1, StringPool.MAIN_SHEET));

		// email
		driver.findElement(By.xpath("//input[@placeholder='Email']"))
				.sendKeys(ExcelUtils.getCellData(row, 2, StringPool.MAIN_SHEET));

		// issue
		Function.dropDown(driver, row, 3, StringPool.MAIN_SHEET);

		// message
		driver.findElement(By.xpath("//textarea[@placeholder='Message']"))
				.sendKeys(ExcelUtils.getCellData(row, 4, StringPool.MAIN_SHEET));

		// click on the submit button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("testEnquiry:end");
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
	public void testAdminSearchEnquiry() throws Exception {

		logger.debug("testAdminSearchEnquiry:start");
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the enquiry
		clientWebDriver.findElement(By.xpath("//html/body/app-root/app-header/div[2]/ul/li[8]/a")).click();

		// search by name or email
		searchEnquiry(email);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// use Assert to check for the text searched
		String name = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-enquiry/div/div[2]/div/div[2]/div/div[1]"))
				.getText();
		Assert.assertEquals(name, "Anjali");

		String email = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-enquiry/div/div[2]/div/div[2]/div/div[2]"))
				.getText();
		Assert.assertEquals(email, "anjali.singh12@gmail.com");

		String message = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-enquiry/div/div[2]/div/div[2]/div/div[3]"))
				.getText();
		Assert.assertEquals(message, "This is testing message.");

		String issue = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-enquiry/div/div[2]/div/div[2]/div/div[4]"))
				.getText();
		Assert.assertEquals(issue, "Technical");
		Thread.sleep(1000);

		// date check calling function
		//Assert.assertEquals(clientWebDriver.getPageSource().contains(dateFormat()), true);

		logger.debug("testAdminSearchEnquiry:end");
	}

	@Test(dependsOnMethods = { "testAdminSearchEnquiry" })
	public void testAdminDeleteEnquiry() throws Exception {

		logger.debug("testAdminDeleteEnquiry:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the delete icon
		WebElement delete = clientWebDriver
				.findElement(By.xpath("html/body/app-root/div/app-enquiry/div/div[2]/div/div[2]/div[1]/div[6]/a/i"));
		delete.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the yes button for deleting the enquiry
		WebElement confirm = clientWebDriver
				.findElement(By.xpath("html/body/app-root/div/app-enquiry/div[2]/div/div/button[1]"));
		confirm.click();
		Thread.sleep(4000);

		// After deleting the enquiry ,searching it again.
		searchEnquiry(email);
		String records = clientWebDriver
				.findElement(By.xpath("//html/body/app-root/div/app-enquiry/div/no-data/div/h2")).getText();
		Assert.assertEquals(records, "No Enquiries Yet.");

		logger.debug("testAdminDeleteEnquiry:end");

	}

//	private String dateFormat() {
//		DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy, h:mm a");
//		Calendar calender = Calendar.getInstance();
//		String todayDate = dateFormat.format(calender.getTime());
//		return todayDate;
//
//	}

	private void searchEnquiry(String email) throws Exception {

		WebElement search = clientWebDriver.findElement(By.xpath("//input[@placeholder='Search...']"));
		search.clear();
		search.sendKeys(email);
		Thread.sleep(Utils.MAX_WAIT_TIME);

	}

	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
		clientWebDriver.close();
		clientWebDriver.quit();
		
	}

}
