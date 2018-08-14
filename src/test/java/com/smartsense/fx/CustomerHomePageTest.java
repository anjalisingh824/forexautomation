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

import com.smartsense.fx.customer.page.HomePage;

public class CustomerHomePageTest {
	
	final static Logger logger = Logger.getLogger(CustomerHomePageTest.class);
	
	private WebDriver webDriver;
	
	private HomePage homePage;
	
	@BeforeClass
	public void initTestCase() throws InterruptedException{
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver",StringPool.CHROME_DRIVER_LOCATION);
		webDriver = new ChromeDriver();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		webDriver.get(StringPool.CUSTOMER_URL);
		
		homePage = new HomePage(webDriver);
	}
	
	@Test(priority=0)
	public void testWhatWeDo() throws InterruptedException{
		Thread.sleep(Utils.MAX_WAIT_TIME);
		homePage.clickWhatWeDo();
		Thread.sleep(Utils.MIN_WAIT_TIME);
		WebElement webElement = webDriver.findElement(By.xpath(".//*[@id='whatWeDo']/div[1]/div[1]/p"));
		String text = webElement.getText();
		Assert.assertTrue(text.equals("Complete trades with entries, exits and Updates in real time."));
		String className = homePage.getWhatWeDoMenu().getAttribute("class");
		Assert.assertTrue(className.equals("active"));
	}
	
	@Test
	public void testWhyUs() throws InterruptedException{
		Thread.sleep(Utils.MIN_WAIT_TIME);
		homePage.clickWhyUs();
		Thread.sleep(Utils.MIN_WAIT_TIME);
		WebElement element = webDriver.findElement(By.xpath(".//*[@id='whyForex']/div[1]/div[1]/h3"));
		String text = element.getText();
		Assert.assertTrue(text.contains("WHY THE"));
		String className = homePage.getWhyUsMenu().getAttribute("class");
		Assert.assertTrue(className.equals("active"));
	}
	
	@AfterClass
	public void tearDown()
	{
		webDriver.close();
		webDriver.quit();
	}
}
