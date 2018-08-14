package com.smartsense.fx;

import java.text.DecimalFormat;
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

/**
 * This class test following test
 * 1. Admin login
 * 2. Create new pending, but limit signal for GBPUSD
 * 3. Search signal at Admin side
 * 4. Search Signal at customer side
 * @author nitin
 *
 */
public class SignalTest {
	private WebDriver webDriver;

	private WebDriver clientWebDriver;

	final static Logger logger = Logger.getLogger(FAQTest.class);
	
	double minEntry;
	double maxEntry;
	double slPrice;
	double tp1Price;
	double tp2Price;
	String etdText = "2 days";
	double askPrice;
	boolean changeSL;
	int signalId ;
	String type = "GBPUSD";
	
	String commentText = StringPool.AUTOMATION_PREFIX+System.currentTimeMillis();
	
	@BeforeClass
	public void initTestCase() throws InterruptedException{
		logger.debug("====================init start======================");
		System.setProperty("webdriver.chrome.driver",StringPool.CHROME_DRIVER_LOCATION);
		webDriver = new ChromeDriver();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
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
	public void testAddSignal() throws InterruptedException{
		logger.debug("testAddSignal : start");
		Thread.sleep(Utils.MAX_WAIT_TIME);
		WebElement signalMenu = webDriver.findElement(By.xpath("html/body/app-root/app-header/div[2]/ul/li[3]/a"));
		signalMenu.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		WebElement addBtn = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[3]/div[1]/div/a[2]/i"));
		addBtn.click();
		Thread.sleep(Utils.MIN_WAIT_TIME);
		
		WebElement name = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[2]/form/div/div[1]/div[1]/div/input"));
		name.clear();
		name.sendKeys(type);
		Thread.sleep(Utils.MIN_WAIT_TIME);
		WebElement intrument = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[2]/form/div/div[1]/div[1]/div/ngui-auto-complete/div/ul/li/span"));
		intrument.click();
		Thread.sleep(2000);
		String currentPrice = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[2]/form/div/div[2]/label")).getText();
		askPrice = Double.parseDouble(currentPrice.split("/")[0]);
		logger.debug("currentPrice : "+currentPrice+" : askPrice : "+askPrice);
		
		WebElement statusDiv = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[2]/form/div/div[1]/div[2]/md-select/div"));
		statusDiv.click();
		Thread.sleep(Utils.MIN_WAIT_TIME);
		WebElement statusPending = webDriver.findElement(By.xpath("//md-option[contains(text(), 'Pending')]"));
		statusPending.click();
		
		WebElement buyLimit = webDriver.findElement(By.xpath("//div[contains(text(), 'Buy limit')]"));
		buyLimit.click();
		
		WebElement min = webDriver.findElement(By.xpath("//input[@placeholder='Min Entry Range']"));
		WebElement max = webDriver.findElement(By.xpath("//input[@placeholder='Max Entry Range']"));
		WebElement tp1 = webDriver.findElement(By.xpath("//input[@placeholder='TP1']"));
		WebElement tp2 = webDriver.findElement(By.xpath("//input[@placeholder='TP2']"));
		WebElement sl = webDriver.findElement(By.xpath("//input[@placeholder='Stop Loss']"));
		WebElement etd = webDriver.findElement(By.xpath("//input[@placeholder='ETD']"));
		WebElement changeSL = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[2]/form/div/div[1]/div[9]/md-checkbox/label/span"));
		WebElement comment = webDriver.findElement(By.xpath("//textarea[@placeholder='Comment']"));
		WebElement saveBtn = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[2]/form/div/div[1]/div[11]/button[1]"));
		
		min.clear();
		max.clear();
		tp1.clear();
		tp2.clear();
		sl.clear();
		etd.clear();
		comment.clear();
		
		minEntry = askPrice-0.2;
		maxEntry = askPrice-0.1;
		
		tp1Price = askPrice+0.2;
		tp2Price = askPrice+0.1;
		slPrice = askPrice-0.3;
		
		min.sendKeys(formatVal(minEntry));
		max.sendKeys(formatVal(maxEntry));
		
		sl.sendKeys(formatVal(slPrice));
		tp1.sendKeys(formatVal(tp1Price));
		tp2.sendKeys(formatVal(tp2Price));
		etd.sendKeys(etdText);
		changeSL.click();
		comment.sendKeys(commentText);
		saveBtn.click();
		
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		
		//
		signalMenu = webDriver.findElement(By.xpath("html/body/app-root/app-header/div[2]/ul/li[3]/a"));
		signalMenu.click();
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		WebElement pendingSearch = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div/div/input"));
		pendingSearch.clear();
		pendingSearch.sendKeys(commentText);
		Thread.sleep(Utils.MAX_WAIT_TIME);
		pendingSearch.click();
		Thread.sleep(Utils.MIN_WAIT_TIME);
		
		Assert.assertTrue(webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[1]")).isDisplayed());
		WebElement signalIdDiv = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[1]"));
		signalId = Integer.parseInt(signalIdDiv.getText());
		
		String intrumentType = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div[1]/div[3]")).getText().trim();
		String action = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[4]")).getText().trim();
		String range = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[5]")).getText().trim();
		String slValue = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[6]")).getText().trim();
		String etdval = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[7]")).getText().trim();
		String tp1Val  = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[8]")).getText().trim();
		String tp2Val = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[9]")).getText().trim();
		String currentVal = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[10]")).getText().trim();
		String status = webDriver.findElement(By.xpath("html/body/app-root/div/app-signal/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[11]/button")).getText().trim();
		
		Assert.assertTrue(intrumentType.equals(type));
		Assert.assertTrue(action.equals("Buy Limit"));
		
		logger.debug("range : "+range);
		Assert.assertTrue(range.contains(String.valueOf(formatVal(minEntry))));
		Assert.assertTrue(range.contains(String.valueOf(formatVal(maxEntry))));
		
		Assert.assertTrue(slValue.equals(formatVal(slPrice)));
		Assert.assertTrue(tp1Val.equals(formatVal(tp1Price)));
		Assert.assertTrue(tp2Val.equals(formatVal(tp2Price)));
		Assert.assertTrue(etdText.equals(etdval));
		//Assert.assertFalse(currentVal.equals("-"));
		Assert.assertTrue(status.equals("Pending"));;
		
		logger.debug("testAddSignal : end");
	}
	
	/*@Test
	public void testSIgnalInEmailAfterAdd() throws InterruptedException{
		webDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		ArrayList<String> tabs = new ArrayList<String> (webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(1)); //switches to new tab
	    webDriver.get("https://www.gmail.com");
	    
	    WebElement username = webDriver.findElement(By.name("identifier"));
	    username.clear();
	    username.sendKeys("nitinvavdiya");
	    Thread.sleep(Utils.MAX_WAIT_TIME);
	    WebElement next = webDriver.findElement(By.xpath(".//*[@id='identifierNext']"));
	    next.click();
	    
	    Thread.sleep(10000);
	    WebElement password = webDriver.findElement(By.name("password"));
	    password.clear();
	    password.sendKeys("");
	    
	    WebElement passNext = webDriver.findElement(By.xpath(".//*[@id='passwordNext']"));
	    passNext.click();
	    Thread.sleep(20000);
	    
	    List<WebElement> a = webDriver.findElements(By.xpath("//*[@class='yW']/span"));
        System.out.println(a.size());
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i).getText());
            if (a.get(i).getText().contains("The Forex Channel")) //to click on a specific mail.
            {                                           
                a.get(i).click();
                break;
            }
        }
        Thread.sleep(10000);
        String emailHeader = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/span/div[1]/h3")).getText();
        String id = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/li[1]/span")).getText().trim();
        String intrument = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/span/li[1]/span")).getText().trim();
        String action = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/span/li[2]/span")).getText().trim();
        String range = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/span/li[3]/span")).getText().trim();
        String sl = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/span/li[4]/span")).getText().trim();
        String tp1 = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/span/li[5]/span")).getText().trim();
        String tp2 = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/span/li[6]/span")).getText().trim();
        String comment = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/li[2]/span")).getText().trim();
        String etd = webDriver.findElement(By.xpath(".//*[@id=':ns']/div[1]/div[3]/div[1]/ul/li[3]/span")).getText().trim();
        
        
        logger.debug( " intrumentType : "+intrument+" : action : "+action+" : range : "+range+" : slValue : "+slValue+" : tp1Val"+tp1Val+" : tp2: "+tp2Val+" : currentVal : "+etd );
        
        Assert.assertTrue(emailHeader.equals("New Signal Published"));
        Assert.assertTrue(intrument.equals(type));
		Assert.assertTrue(action.equals("Buy Limit"));
		
		logger.debug("range : "+range);
		Assert.assertTrue(range.contains(String.valueOf(formatVal(minEntry))));
		Assert.assertTrue(range.contains(String.valueOf(formatVal(maxEntry))));
		
		Assert.assertTrue(sl.equals(formatVal(slPrice)));
		Assert.assertTrue(commentText.equals(comment));
		Assert.assertTrue(tp1.equals(formatVal(tp1Price)));
		Assert.assertTrue(tp2.equals(formatVal(tp2Price)));
		Assert.assertTrue(etdText.equals(etd));
		///Assert.assertTrue(signalId.equals(String.valueOf(id)));
		//Assert.assertFalse(currentVal.equals("-"));
	}*/
	
	@Test(dependsOnMethods={"testAddSignal"})
	public void testCustomerLogin() throws InterruptedException {
		Thread.sleep(Utils.MAX_WAIT_TIME);
		
		WebElement loginBtn =clientWebDriver.findElement(By.xpath(".//*[@id='home']/nav/div[1]/ul[2]/li[7]/button[1]"));
		loginBtn.click();
		
		WebElement userNameFeild = clientWebDriver.findElement(By.xpath("html/body/app-root/app-login/div/div/div/div[2]/div/form/div[1]/div/input"));
		WebElement passwordFeild = clientWebDriver.findElement(By.xpath("html/body/app-root/app-login/div/div/div/div[2]/div/form/div[2]/div/input"));
		
		userNameFeild.clear();
		passwordFeild.clear();
		
		userNameFeild.sendKeys(StringPool.CUSTOMER_USERNAME);
		passwordFeild.sendKeys(StringPool.CUSTOMER_PASSWORD);
		
		WebElement loginBtn1 = clientWebDriver.findElement(By.xpath("html/body/app-root/app-login/div/div/div/div[2]/div/form/div[4]/button"));
		loginBtn1.click();
		
		Thread.sleep(Utils.MAX_WAIT_TIME);
	}
	
	@Test(dependsOnMethods={"testCustomerLogin"})
	public void testSignalAtCustomerAfterAdd() throws InterruptedException{
		WebElement search = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[1]/div/input"));
		search.sendKeys(String.valueOf(signalId));
		Thread.sleep(Utils.MAX_WAIT_TIME);
		search.click();
		Thread.sleep(Utils.MIN_WAIT_TIME);
		
		
		String intrumentType = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[3]")).getText().trim();
		String action = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[4]")).getText().trim();
		String range = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[5]")).getText().trim();
		String slValue = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[6]")).getText().trim();
		String tp1Val  = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[7]")).getText().trim();
		String tp2Val = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[8]")).getText().trim();
		String etd = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[9]")).getText().trim();
		String status = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[11]/button")).getText().trim();
		String currentPrice = clientWebDriver.findElement(By.xpath("html/body/app-root/app-signal-channel/div/div/div[2]/div[2]/div/div[2]/div/div[10]")).getText().trim();
		logger.debug("status : "+status+" : intrumentType : "+intrumentType+" : action : "+action+" : range : "+range+" : slValue : "+slValue+" : tp1Val"+tp1Val+" : tp2: "+tp2Val+" : currentVal : "+etd );
		Assert.assertTrue(intrumentType.equals(type));
		Assert.assertTrue(action.equals("Buy Limit"));
		
		logger.debug("range : "+range);
		Assert.assertTrue(range.contains(String.valueOf(formatVal(minEntry))));
		Assert.assertTrue(range.contains(String.valueOf(formatVal(maxEntry))));
		
		Assert.assertTrue(slValue.equals(formatVal(slPrice)));
		Assert.assertTrue(tp1Val.equals(formatVal(tp1Price)));
		Assert.assertTrue(tp2Val.equals(formatVal(tp2Price)));
		
		//Assert.assertFalse(currentVal.equals("-"));
		Assert.assertTrue(status.equals("Pending"));;
	}
	private String formatVal(double val){
		DecimalFormat decimalFormat = new DecimalFormat("#.0000");
		return decimalFormat.format(val);
	}
}
