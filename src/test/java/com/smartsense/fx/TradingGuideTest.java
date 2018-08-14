package com.smartsense.fx;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TradingGuideTest {

	private WebDriver driver;
	private WebDriver clientWebDriver;
	final static Logger logger = Logger.getLogger(TradingGuideTest.class);

	@BeforeClass
	public void setUp() throws Exception {
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
		driver = new ChromeDriver();
		driver.get(StringPool.ADMIN_URL);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		clientWebDriver = new ChromeDriver();
		clientWebDriver.get(StringPool.CUSTOMER_URL);
		clientWebDriver.manage().deleteAllCookies();
		clientWebDriver.manage().window().maximize();
		clientWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		logger.debug("====================init start======================");

	}

	@Test(priority = 0)
	public void testAdminLogin() throws Exception {

		logger.debug("testAdminLogin:start");
		// clear the fields first
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).clear();

		// pass the values
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(StringPool.ADMIN_USERNAME);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(StringPool.ADMIN_PASSWORD);

		Thread.sleep(2000);
		// click on the login button now
		driver.findElement(By.xpath("html/body/app-root/div/login/div/div/form/button")).click();

		logger.debug("testAdminLogin:end");
	}

	@Test(dependsOnMethods = { "testAdminLogin" })
	public void addTradingGuide() throws Exception {

		logger.debug("addTradingGuide:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the trading guide
		driver.findElement(By.linkText("Trading Guide")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the add Trading guide
		driver.findElement(By.xpath("//a[@title='Add']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// expand the area
		driver.findElement(By.xpath("//html/body/app-root/app-header/div[1]/div/a/i")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// select the instrument
		driver.findElement(By.xpath("//md-select[@placeholder='Instrument']")).click();
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		WebElement selectInstrument = driver.findElement(By.xpath("//md-option[contains(text(),'AUDUSD')]"));
		action.moveToElement(selectInstrument).click().build().perform();
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// select the ST Bias
		driver.findElement(By.xpath("//md-select[@placeholder='ST Bias']")).click();
		Thread.sleep(2000);
		Function.dropDownTradingGuide(driver, 52, 1, StringPool.MAIN_SHEET);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// targets
		driver.findElement(By.xpath("//input[@placeholder='Targets']")).sendKeys("10");

		// Invalidation
		driver.findElement(By.xpath("//input[@placeholder='Invalidation']")).sendKeys("9");

		// S1
		driver.findElement(By.xpath("//input[@placeholder='S1']")).sendKeys("8");

		// S2
		driver.findElement(By.xpath("//input[@placeholder='S2']")).sendKeys("7");

		// S3
		driver.findElement(By.xpath("//input[@placeholder='S3']")).sendKeys("6");

		// R1
		driver.findElement(By.xpath("//input[@placeholder='R1']")).sendKeys("5");

		// R2
		driver.findElement(By.xpath("//input[@placeholder='R2']")).sendKeys("4");

		// R3
		driver.findElement(By.xpath("//input[@placeholder='R3']")).sendKeys("3");

		// add image
		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys("/home/smart/Downloads/my wallpapers/301627794.jpg");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the save button
		driver.findElement(By.xpath("//button[contains(text(),'SAVE')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("addTradingGuide:end");

	}

	@Test(dependsOnMethods = { "addTradingGuide" })
	public void editTradingGuide() throws Exception {

		logger.info("editTradingGuide:start");

		// click on the add Trading guide
		driver.findElement(By.xpath("//a[@title='Add']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// select the instrument
		driver.findElement(By.xpath("//md-select[@placeholder='Instrument']")).click();
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		WebElement selectInstrument = driver.findElement(By.xpath("//md-option[contains(text(),'GOLD')]"));
		action.moveToElement(selectInstrument).click().build().perform();
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// select the ST Bias
		driver.findElement(By.xpath("//md-select[@placeholder='ST Bias']")).click();
		Thread.sleep(2000);
		Function.dropDownTradingGuide(driver, 52, 2, StringPool.MAIN_SHEET);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// targets
		driver.findElement(By.xpath("//input[@placeholder='Targets']")).sendKeys("10");

		// Invalidation
		driver.findElement(By.xpath("//input[@placeholder='Invalidation']")).sendKeys("9");

		// S1
		driver.findElement(By.xpath("//input[@placeholder='S1']")).sendKeys("8");

		// S2
		driver.findElement(By.xpath("//input[@placeholder='S2']")).sendKeys("7");

		// S3
		driver.findElement(By.xpath("//input[@placeholder='S3']")).sendKeys("6");

		// R1
		driver.findElement(By.xpath("//input[@placeholder='R1']")).sendKeys("5");

		// R2
		driver.findElement(By.xpath("//input[@placeholder='R2']")).sendKeys("4");

		// R3
		driver.findElement(By.xpath("//input[@placeholder='R3']")).sendKeys("3");

		// add image
		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys("/home/smart/Downloads/my wallpapers/minions-wallpaper_064623964_257.jpg");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the save button
		driver.findElement(By.xpath("//button[contains(text(),'SAVE')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.info("editTradingGuide:end");

	}

	@Test(dependsOnMethods = { "addTradingGuide" })
	public void editTradingGuideNext() throws Exception {

		logger.debug("editTradingGuideNext:start");
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the add Trading guide
		driver.findElement(By.xpath("//a[@title='Add']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// select the instrument
		driver.findElement(By.xpath("//md-select[@placeholder='Instrument']")).click();
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		WebElement selectInstrument = driver.findElement(By.xpath("//md-option[contains(text(),'EURGBP')]"));
		action.moveToElement(selectInstrument).click().build().perform();
		Thread.sleep(Utils.MIN_WAIT_TIME);

		// select the ST Bias
		driver.findElement(By.xpath("//md-select[@placeholder='ST Bias']")).click();
		Thread.sleep(2000);
		Function.dropDownTradingGuide(driver, 52, 3, StringPool.MAIN_SHEET);
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// targets
		driver.findElement(By.xpath("//input[@placeholder='Targets']")).sendKeys("9");

		// Invalidation
		driver.findElement(By.xpath("//input[@placeholder='Invalidation']")).sendKeys("8");

		// S1
		driver.findElement(By.xpath("//input[@placeholder='S1']")).sendKeys("7");

		// S2
		driver.findElement(By.xpath("//input[@placeholder='S2']")).sendKeys("6");

		// S3
		driver.findElement(By.xpath("//input[@placeholder='S3']")).sendKeys("5");

		// R1
		driver.findElement(By.xpath("//input[@placeholder='R1']")).sendKeys("4");

		// R2
		driver.findElement(By.xpath("//input[@placeholder='R2']")).sendKeys("3");

		// R3
		driver.findElement(By.xpath("//input[@placeholder='R3']")).sendKeys("2");

		// add image
		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys("/home/smart/Downloads/my wallpapers/maruti-suzuki-baleno-image-11521.png");

		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the save button
		driver.findElement(By.xpath("//button[contains(text(),'SAVE')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("editTradingGuideNext:end");

	}

	@Test(dependsOnMethods = { "editTradingGuideNext" })
	public void checkTradingGuideCustomerSide() throws Exception {

		logger.debug("checkTradingGuideCustomerSide:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		//click on the login button
		clientWebDriver.findElement(By.xpath("//button[contains(text(),'LOGIN')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		

		// username or email
		clientWebDriver.findElement(By.xpath("//input[@type='text']"))
				.sendKeys(ExcelUtils.getCellData(42, 1, StringPool.MAIN_SHEET));

		// password
		clientWebDriver.findElement(By.xpath("//input[@type='password']"))
				.sendKeys(ExcelUtils.getCellData(42, 2, StringPool.MAIN_SHEET));

		// click on the login button
		clientWebDriver.findElement(By.xpath("//button[contains(text(),'LOGIN')]")).click();
		Thread.sleep(2000);

		// click on the trading guide
		clientWebDriver
				.findElement(By
						.xpath("//html/body/app-root/app-signal-channel/app-header/div/div[1]/div[1]/div[1]/div[2]/ul/li[2]/a/img"))
				.click();
		
	    Thread.sleep(Utils.MAX_WAIT_TIME);
	    
	    //assert for the added trading guide
	    String instrumentOne=clientWebDriver.findElement(By.xpath("//html/body/app-root/app-trading-guide/div/div/div/div[2]/div/div[2]/div[1]/div[1]")).getText();
	    Assert.assertEquals(instrumentOne, "AUDUSD");
	    
		String instrumentSecond=clientWebDriver.findElement(By.xpath("//html/body/app-root/app-trading-guide/div/div/div/div[2]/div/div[2]/div[2]/div[1]/")).getText();
        Assert.assertEquals(instrumentSecond, "GOLD");
		
		String instrumentThird=clientWebDriver.findElement(By.xpath("//html/body/app-root/app-trading-guide/div/div/div/div[2]/div/div[2]/div[3]/div[1]")).getText();
		Assert.assertEquals(instrumentThird, "EURGBP");
		
		
		logger.debug("checkTradingGuideCustomerSide:end");
	}

	 @AfterTest()
	
	 public void tearDown()
	 {
	 driver.close();
	 driver.quit();
	 
	 clientWebDriver.close();
	 clientWebDriver.quit();
	 }
	

}
