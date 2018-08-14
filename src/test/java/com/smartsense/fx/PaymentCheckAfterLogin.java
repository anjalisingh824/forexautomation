package com.smartsense.fx;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

public class PaymentCheckAfterLogin {
	private WebDriver driver;
	final static Logger logger = Logger.getLogger(PaymentCheckAfterLogin.class);

	@BeforeClass
	public void setUp() throws Exception {
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
		driver = new ChromeDriver();
		driver.get(StringPool.CUSTOMER_URL);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		

	}

	@Test(priority = 0)
	public void loginPage() throws Exception {

		logger.debug("loginPage:start");

		// click on the login button
		driver.findElement(By.xpath("//button[contains(text(),'LOGIN')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		int row = 42;
		// username or email
		driver.findElement(By.xpath("//input[@type='text']"))
				.sendKeys(ExcelUtils.getCellData(row, 1, StringPool.MAIN_SHEET));

		// password
		driver.findElement(By.xpath("//input[@type='password']"))
				.sendKeys(ExcelUtils.getCellData(row, 2, StringPool.MAIN_SHEET));

		// click on the login button
		driver.findElement(By.xpath("//button[contains(text(),'LOGIN')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("loginPage:end");

	}

	@Test(dependsOnMethods = { "loginPage" })
	public void addNewPackagePaymentPage() throws Exception {

		logger.debug("addNewPackagePaymentPage:start");

		// click on the icon
		driver.findElement(By.className("user-profile")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the profile
		driver.findElement(By.linkText("Profile")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// add new package
		driver.findElement(By.xpath("//button[contains(text(),'Add new plan')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// select package
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

		// click on the paynow
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// check for the discount value present on the page by asserting
		Assert.assertEquals(driver.getPageSource().contains(StringPool.SIGNAL_QUARTERLY_DISCOUNT), true);

		// click on the checkout button
		driver.findElement(By.xpath("//*[@id='payu-payment-form']/input[2]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// payment page
		// click on the payza
		driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBodyMain_main_lnkMemberView']/span/img")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the continue button
		driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the continue button
		driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the make payment button
		driver.findElement(By.xpath("//input[@value='MAKE PAYMENT']")).click();
		Thread.sleep(25000);

		// go to the profile page again
		// click on the icon for profile
		driver.findElement(By.className("user-profile")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		//click on the profile
		driver.findElement(By.linkText("Profile")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		logger.debug("addNewPackagePaymentPage:end");

	}

	@Test(dependsOnMethods = { "addNewPackagePaymentPage" })
	public void renewPackagePaymentTest() throws Exception {

		logger.debug("renewPackagePaymentTest:start");

		Thread.sleep(Utils.MAX_WAIT_TIME);
		// click on the renew package
		driver.findElement(By
				.xpath("//html/body/app-root/app-profile/div/div/div/div[1]/div[2]/div[6]/div[2]/div/table/tbody/tr[1]/td[9]/button"))
				.click();
		
		
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the paynow
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// check for the discount value present on the page by asserting
		Assert.assertEquals(driver.getPageSource().contains(StringPool.SIGNAL_QUARTERLY_DISCOUNT), true);

		// click on the checkout button
		driver.findElement(By.xpath("//*[@id='payu-payment-form']/input[2]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// payment page
		// click on the payza
		driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBodyMain_main_lnkMemberView']/span/img")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the continue button
		driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the continue button
		driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the make payment button
		driver.findElement(By.xpath("//input[@value='MAKE PAYMENT']")).click();
		Thread.sleep(25000);

		logger.debug("renewPackagePaymentTest:end");

	}

	@Test(dependsOnMethods = { "renewPackagePaymentTest" })
	public void testPaymentOnTheHomePage() throws Exception {
		logger.debug("testPaymentOnTheHomePage:start");

		// click on the packages
		driver.findElement(By.linkText("Packages")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// select quarterly
		driver.findElement(By.xpath("//*[@id='packages']/div/div[2]/div/a[2]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the subscription button for signal + quarterly
		driver.findElement(By.xpath("//*[@id='packages']/div/div[4]/div[1]/div/div/button")).click();
		Thread.sleep(7000);

		// click on the paynow
		driver.findElement(By.xpath("//button[contains(text(),'Pay now')]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// check for the discount value present on the page by asserting
		Assert.assertEquals(driver.getPageSource().contains(StringPool.SIGNAL_QUARTERLY_DISCOUNT), true);

		// click on the checkout button
		driver.findElement(By.xpath("//*[@id='payu-payment-form']/input[2]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// payment page
		// click on the payza
		driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBodyMain_main_lnkMemberView']/span/img")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the continue button
		driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the continue button
		driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the make payment button
		driver.findElement(By.xpath("//input[@value='MAKE PAYMENT']")).click();
		Thread.sleep(25000);

		logger.debug("testPaymentOnTheHomePage:end");

	}
	
	
	@AfterClass
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}

}
