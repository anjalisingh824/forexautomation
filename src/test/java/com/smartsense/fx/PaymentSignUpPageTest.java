package com.smartsense.fx;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 1.Signup and then payment 2.Login and add new package 3.Login and renew
 * package
 * 
 */
public class PaymentSignUpPageTest {

	private WebDriver driver;
	final static Logger logger = Logger.getLogger(PaymentSignUpPageTest.class);

	@BeforeClass
	public void setUp() throws Exception {
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver", StringPool.CHROME_DRIVER_LOCATION);
		driver = new ChromeDriver();
		driver.get(StringPool.CUSTOMER_URL);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		logger.debug("====================init start======================");

	}

	@Test(priority = 0)
	public void testSignUpPayment() throws Exception {
		logger.debug("testSignUpPayment:start");

		// click on the home page packages
		driver.findElement(By.linkText("Packages")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the quarterly
		driver.findElement(By.xpath("//*[@id='packages']/div/div[2]/div/a[2]")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// subscribe to signal + quarterly
		driver.findElement(By.xpath("//*[@id='packages']/div/div[4]/div[1]/div/div/button")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// now fill the signup form
		int row = 48;

		// firstname
		driver.findElement(By.xpath("//input[@placeholder='First name']"))
				.sendKeys(ExcelUtils.getCellData(row, 1, StringPool.MAIN_SHEET));

		// lastname
		driver.findElement(By.xpath("//input[@placeholder='Last name']"))
				.sendKeys(ExcelUtils.getCellData(row, 2, StringPool.MAIN_SHEET));

		// username
		driver.findElement(By.xpath("//input[@placeholder='User name']"))
				.sendKeys(ExcelUtils.getCellData(row, 3, StringPool.MAIN_SHEET));

		// password
		driver.findElement(By
				.xpath("//html/body/app-root/app-register/div/div[1]/div[1]/div[2]/form/div[1]/div/div[4]/div/div/input"))
				.sendKeys(ExcelUtils.getCellData(row, 4, StringPool.MAIN_SHEET));

		// confirm password
		driver.findElement(By
				.xpath("//html/body/app-root/app-register/div/div[1]/div[1]/div[2]/form/div[1]/div/div[5]/div/div/input"))
				.sendKeys(ExcelUtils.getCellData(row, 5, StringPool.MAIN_SHEET));

		// country
		driver.findElement(By.xpath("//input[@placeholder='Country']")).sendKeys("ind");
		WebElement name = driver.findElement(By.className("ng2-auto-complete"));
		List<WebElement> storeTag = name.findElements(By.tagName("span"));
		String str = ExcelUtils.getCellData(row, 6, StringPool.MAIN_SHEET);
		for (int i = 0; i < storeTag.size(); i++) {
			if (storeTag.get(i).getText().equalsIgnoreCase(str)) {
				System.out.println(storeTag.get(i).getText());
				storeTag.get(i).click();
				Thread.sleep(2000);
				break;
			}
		}

		// mobile number
		driver.findElement(By.xpath("//input[@placeholder='Contact number']"))
				.sendKeys(ExcelUtils.getCellData(row, 7, StringPool.MAIN_SHEET));

		// email
		driver.findElement(By.xpath("//input[@placeholder='Email']"))
				.sendKeys(ExcelUtils.getCellData(row, 8, StringPool.MAIN_SHEET));

		// account size
		Function.dropDown(driver, row, 9, StringPool.MAIN_SHEET);
		
		//agree to the terms and condition
		driver.findElement(By.xpath("//html/body/app-root/app-register/div/div[1]/div[1]/div[2]/form/div[1]/div/div[13]/div/div/label")).click();
		Thread.sleep(Utils.MAX_WAIT_TIME);

		// click on the update button
		driver.findElement(By.xpath("//button[contains(text(),'Update')]")).click();

		Thread.sleep(1000);

		logger.debug("testSignUpPayment:end");
		
		
	}

}
