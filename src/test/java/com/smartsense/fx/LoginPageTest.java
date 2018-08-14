package com.smartsense.fx;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.smartsense.fx.customer.page.Login;

public class LoginPageTest {
	
	private WebDriver driver;
	private Login login;
	final static Logger logger = Logger.getLogger(LoginPageTest.class);
	@BeforeClass
	public void setUp() throws Exception {
	
	    logger.debug("==========================init start================================");	
		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
		driver = new ChromeDriver();
		driver.get(StringPool.CUSTOMER_URL);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        login=new Login(driver);

	}

	@Test()
	public void loginPageTest() throws Exception
	{
		Thread.sleep(5000);
		login.clickLogin();
		Assert.assertEquals(driver.getTitle(), "ForexAnalysis");
		Thread.sleep(2000);
		login.loginByEmail.sendKeys(ExcelUtils.getCellData(42, 1, StringPool.MAIN_SHEET));
		login.passwordEnter.sendKeys(ExcelUtils.getCellData(42, 2, StringPool.MAIN_SHEET));
		login.clickLogin();
		Thread.sleep(2000);
		
		
	}
	@AfterClass
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}
	
}
