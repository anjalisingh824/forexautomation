package com.smartsense.fx;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This class test Following step
 * 1. Login in admin
 * 2. Add new FAQ
 * 3. search newly added FAQ ad Admin side
 * 4. Search newly Added FAQ at customer side
 * 5. disable FAQ at Admin side
 * 6. search added FAQ ad Admin side
 * 7. Search Added FAQ at customer side 
 * 8. Enable FAQ
 * 9. search added FAQ ad Admin side
 * 10. Search Added FAQ at customer side
 * 11. Delete FAQ at Amdin side
 * 12. search added FAQ ad Admin side
 * 13. Search Added FAQ at customer side
 * @author nitin
 *
 */
public class FAQTest {


	private WebDriver webDriver;
	
	private WebDriver clientWebDriver;
	
	private String quation = StringPool.AUTOMATION_PREFIX+System.currentTimeMillis()+"_Question";
	private String answers = StringPool.AUTOMATION_PREFIX+System.currentTimeMillis()+"_answers";
	
	final static Logger logger = Logger.getLogger(FAQTest.class);
	
	@BeforeClass
	public void initTestCase() throws InterruptedException{
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver",StringPool.CHROME_DRIVER_LOCATION);
		webDriver = new ChromeDriver();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		webDriver.get(StringPool.ADMIN_URL);
		
		clientWebDriver = new ChromeDriver();
		clientWebDriver.manage().deleteAllCookies();
		clientWebDriver.manage().window().maximize();
		clientWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		clientWebDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		clientWebDriver.get(StringPool.CUSTOMER_URL);
		logger.debug("====================init start======================");
	}

	@AfterClass
	public void afterTest() {
		webDriver.close();
		webDriver.quit();
		
		clientWebDriver.close();
		clientWebDriver.quit();
	}

	@Test(priority=0)
	public void testAdminLogin() throws InterruptedException{
		logger.debug("testAdminLogin : start");
		WebElement adminUserName = webDriver.findElement(By.id("md-input-1"));
		WebElement adminPassword = webDriver.findElement(By.id("md-input-3"));
		WebElement loginBtn = webDriver.findElement(By.xpath("html/body/app-root/div/login/div/div/form/button"));
		adminUserName.clear();
		adminUserName.sendKeys(StringPool.ADMIN_USERNAME);
		adminPassword.clear();
		adminPassword.sendKeys(StringPool.ADMIN_PASSWORD);
		loginBtn.click();
		logger.debug("testAdminLogin : end");
	}
	
	
	@Test(dependsOnMethods={"testAdminLogin"})
	public void testAdminAddFAQ() throws InterruptedException{
		logger.debug("testAdminAddFAQ : start");
		Thread.sleep(5000);
		webDriver.findElement(By.xpath("html/body/app-root/app-header/div[2]/ul/li[6]")).click();
		Thread.sleep(2000);
		webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[1]/a/i")).click();
		Thread.sleep(2000);
		WebElement q = webDriver.findElement(By.xpath(".//*[@id='md-input-5']"));
		q.clear();
		q.sendKeys(quation);
		
		WebElement ans = webDriver.findElement(By.xpath(".//*[@id='md-input-7']"));
		ans.clear();
		ans.sendKeys(answers);
		
		
		webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/form/div/div[1]/div[3]/button[1]")).click();
		Thread.sleep(3000);
		
		searchFAQ(quation);
		WebElement qText = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[1]"));
		WebElement ansText = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[2]"));
		
		Assert.assertTrue(qText.isDisplayed());
		String text = qText.getText();
		Assert.assertEquals(text, quation);
		
		Assert.assertTrue(ansText.isDisplayed());
		text = ansText.getText();
		Assert.assertEquals(text, answers);
		
		
		logger.debug("testAdminAddFAQ : end");
	}
	
		
	@Test(dependsOnMethods={"testAdminAddFAQ"})
	public void testCustomerFAQAfterAdd() throws InterruptedException{
		checkFAQAtCustomerSide(quation, true);
	}
	
	
	/*@Test(dependsOnMethods={"testAdminLogin"})
	public void testAdminEditFAQ() throws InterruptedException{
		logger.debug("testAdminEditFAQ : start");
		searchFAQ(quation);
		quation = quation+"_afterEdit";
		answers = answers+"_afterEdit";
		
		WebElement edit = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[3]/a/i"));
		edit.click();
		Thread.sleep(200);
		
		WebElement q = webDriver.findElement(By.xpath(".//*[@id='md-input-13']"));
		q.clear();
		q.sendKeys(quation);
		
		WebElement ans = webDriver.findElement(By.xpath(".//*[@id='md-input-15']"));
		ans.clear();
		ans.sendKeys(answers);
		
		webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/form/div/div[1]/div[3]/button[1]")).click();
		Thread.sleep(3000);
		
		searchFAQ(quation);
		WebElement records = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[1]"));
		
		WebElement ansText = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[2]"));
		
		Assert.assertTrue(records.isDisplayed());
		String text = records.getText();
		Assert.assertEquals(text, quation);
		
		Assert.assertTrue(ansText.isDisplayed());
		text = ansText.getText();
		Assert.assertEquals(text, answers);
		
		logger.debug("testAdminEditFAQ : start");
	}

	
	@Test(dependsOnMethods={"testAdminEditFAQ"})
	public void testCustomerFAQAfterEdit() throws InterruptedException{
		checkFAQAtCustomerSide(quation, true);
	}
*/	
	@Test(dependsOnMethods={"testCustomerFAQAfterAdd"})
	public void testAdminEnableDisableFAQ() throws InterruptedException{
		logger.debug("testAdminEnableDisableFAQ : Start : disable");
		//disable FAQ
		Thread.sleep(4000);
		WebElement diableButton = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div[1]/div[5]/md-slide-toggle/label/div"));
		diableButton.click();
		Thread.sleep(4000);
		searchFAQ(quation);
		logger.debug("testAdminEnableDisableFAQ : end : disable");
		//FAQ should not visible at customer side
		checkFAQAtCustomerSide(quation, false);
		
		logger.debug("testAdminEnableDisableFAQ : Start : enble");
		//enable FAQ
		WebElement records = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[1]"));
		Assert.assertTrue(records.isDisplayed());
		Thread.sleep(2000);
		diableButton = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[5]/md-slide-toggle/label/div"));
		
		diableButton.click();
		Thread.sleep(4000);
		searchFAQ(quation);
		records = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div/div[1]"));
		Assert.assertTrue(records.isDisplayed());
		
		//FAQ should visible at customer side
		checkFAQAtCustomerSide(quation, true);
		logger.debug("testAdminEnableDisableFAQ : end : enble");
	}
	
	@Test(dependsOnMethods={"testAdminEnableDisableFAQ"})
	public void testAdminDeleteFAQ() throws InterruptedException{
		logger.debug("testAdminDeleteFAQ : start");
		searchFAQ(quation);
		WebElement delete = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[2]/div/div[2]/div[1]/div[4]/a/i"));
		delete.click();
		Thread.sleep(500);
		WebElement confirm = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div[2]/div/div/button[1]"));
		confirm.click();
		Thread.sleep(4000);
		searchFAQ(quation);
		WebElement records = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/no-data/div/h2"));
		Assert.assertTrue(records.isDisplayed());
		logger.debug("testAdminDeleteFAQ : end");
	}
	
	@Test(dependsOnMethods={"testAdminDeleteFAQ"})
	public void testCustomerFAQAfterDelete() throws InterruptedException{
		checkFAQAtCustomerSide(quation, false);
	}

	/**
	 * @throws InterruptedException
	 */
	private void checkFAQAtCustomerSide(String quation, boolean isAdd) throws InterruptedException {
		logger.debug("checkFAQAtCustomerSide : Enter : isAdd : "+isAdd);
		clientWebDriver.navigate().refresh();
		Thread.sleep(5000);
		WebElement faqMenu = clientWebDriver.findElement(By.xpath(".//*[@id='home']/nav/div[1]/ul[2]/li[6]/a"));
		faqMenu.click();
		Thread.sleep(2000);
		boolean test = clientWebDriver.findElements(By.xpath(".//*[@id='faq']/div[1]/div[2]/div[5]/a")).size() != 0;
		if(!test){
			faqMenu.click();
		}
		while(test){
			Thread.sleep(2000);
			JavascriptExecutor jse = ((JavascriptExecutor) webDriver);
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			clientWebDriver.findElement(By.linkText("Load more >")).click();
			Thread.sleep(5000);
			
			WebElement table = clientWebDriver.findElement(By.xpath(".//*[@id='faq']/div[1]/div[2]"));
			test = table.getText().contains("Load more >");
		}
		
		WebElement table = clientWebDriver.findElement(By.id("faq"));
		
		if(isAdd){
			Assert.assertTrue(table.getText().contains(quation));
		}else{
			Assert.assertFalse(table.getText().contains(quation));
		}
		logger.debug("checkFAQAtCustomerSide : Exit : isAdd : "+isAdd);
	}

	/**
	 * @param quation
	 * @throws InterruptedException
	 */
	private void searchFAQ(String quation) throws InterruptedException {
		logger.debug("searchFAQ : start");
		
		Thread.sleep(3000);
		WebElement faqMenu = webDriver.findElement(By.xpath("html/body/app-root/app-header/div[2]/ul/li[6]/a"));
		faqMenu.click();
		Thread.sleep(4000);
		
		WebElement search = webDriver.findElement(By.xpath("html/body/app-root/div/app-faq/div/div[1]/div/input"));
		search.clear();
		search.sendKeys(quation);
		Thread.sleep(4000);
		logger.debug("searchFAQ : end");
	}
}
