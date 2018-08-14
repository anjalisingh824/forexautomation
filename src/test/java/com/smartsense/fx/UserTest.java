package com.smartsense.fx;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTest {

	private WebDriver driver;
	private WebDriver clientWebDriver;
	final static Logger logger = Logger.getLogger(UserTest.class);
	private String email="zeroviy@vpstraffic.com";

	@BeforeClass
	public void setUp() throws Exception {
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
//		driver = new ChromeDriver();
//		driver.get(StringPool.CUSTOMER_URL);
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		clientWebDriver = new ChromeDriver();
		clientWebDriver.manage().deleteAllCookies();
		clientWebDriver.manage().window().maximize();
		clientWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		clientWebDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		clientWebDriver.get(StringPool.ADMIN_URL);
		logger.debug("====================init start======================");

	}

	@Test(priority=0)
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
	
	@Test(dependsOnMethods={"testAdminLogin"})
	public void testEditUser() throws Exception{
		
		logger.debug("testEditUser:start");
		
	    Thread.sleep(Utils.MAX_WAIT_TIME);
		
		//search for the user
	    searchUser(email);
		Thread.sleep(Utils.MIN_WAIT_TIME);
		
		//click on the side menu to make the page larger
		clientWebDriver.findElement(By.xpath("//html/body/app-root/app-header/div[1]/div/a/i")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		//click on the edit button
		clientWebDriver.findElement(By.xpath("//a[@title='Edit']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		//clear the fields
		//first name
		clientWebDriver.findElement(By.xpath("//input[@placeholder='First name']")).clear();
		
		//last name
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Last name']")).clear();
		
		//country
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Country']")).clear();
		
		//email
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Email']")).clear();
		
		//contact number
		clientWebDriver.findElement(By.xpath("//input[@placeholder='Contact number']")).clear();
		
		Thread.sleep(7000);
		
	}
	
	
	
	
	private void searchUser(String email) throws Exception {
		WebElement search = clientWebDriver.findElement(By.xpath("//input[@placeholder='Email, Name...']"));
		search.clear();
		search.sendKeys(email);
		Thread.sleep(Utils.MAX_WAIT_TIME);
	}
	
	
	
	
//	@AfterTest
//	public void tearDown()
//	{
//		clientWebDriver.close();
//		clientWebDriver.quit();
//	}
	

}
