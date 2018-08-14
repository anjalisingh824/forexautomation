package com.smartsense.fx.customer.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * 
 * @author nitin
 *
 */
public class HomePage {

	
	private WebDriver webdriver;
	
	public HomePage(WebDriver webdriver) {
		this.webdriver = webdriver;
		PageFactory.initElements(webdriver, this);
	}
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[1]/a")
	private WebElement homeMenu;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[2]/a")
	private WebElement whatWeDoMenu;
	
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[3]/a")
	private WebElement whyUsMenu;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[4]/a")
	private WebElement packagesMenu;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[5]/a")
	private WebElement forexAnalysisMenu;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[6]/a")
	private WebElement faqMenu;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[7]/button[1]")
	private WebElement loginBtn;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[7]/button[2]")
	WebElement signUpBtn;
	
	@FindBy(xpath=".//*[@id='home']/nav/div[1]/ul[2]/li[8]/a")
	WebElement contactUsMenu;
	
	public WebDriver getWebdriver() {
		return webdriver;
	}

	public void setWebdriver(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	public WebElement getHomeMenu() {
		return homeMenu;
	}

	public void setHomeMenu(WebElement homeMenu) {
		this.homeMenu = homeMenu;
	}

	public WebElement getWhatWeDoMenu() {
		return whatWeDoMenu;
	}

	public void setWhatWeDoMenu(WebElement whatWeDoMenu) {
		this.whatWeDoMenu = whatWeDoMenu;
	}

	public WebElement getWhyUsMenu() {
		return whyUsMenu;
	}

	public void setWhyUsMenu(WebElement whyUsMenu) {
		this.whyUsMenu = whyUsMenu;
	}

	public WebElement getPackagesMenu() {
		return packagesMenu;
	}

	public void setPackagesMenu(WebElement packagesMenu) {
		this.packagesMenu = packagesMenu;
	}

	public WebElement getForexAnalysisMenu() {
		return forexAnalysisMenu;
	}

	public void setForexAnalysisMenu(WebElement forexAnalysisMenu) {
		this.forexAnalysisMenu = forexAnalysisMenu;
	}

	public WebElement getFaqMenu() {
		return faqMenu;
	}

	public void setFaqMenu(WebElement faqMenu) {
		this.faqMenu = faqMenu;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(WebElement loginBtn) {
		this.loginBtn = loginBtn;
	}

	public WebElement getSignUpBtn() {
		return signUpBtn;
	}

	public void setSignUpBtn(WebElement signUpBtn) {
		this.signUpBtn = signUpBtn;
	}

	public WebElement getContactUsMenu() {
		return contactUsMenu;
	}

	public void setContactUsMenu(WebElement contactUsMenu) {
		this.contactUsMenu = contactUsMenu;
	}

	public void  clickHome(){
		homeMenu.click();
	}
	
	public void clickWhatWeDo(){
		whatWeDoMenu.click();
	}
	
	public void clickWhyUs(){
		whyUsMenu.click();
	}
	
	public void clickPackages(){
		packagesMenu.click();
	}
	
	public void clickFaq(){
		faqMenu.click();
	}
	
	public void clickLogin(){
		loginBtn.click();
	}
	public void clickSignUp(){
		signUpBtn.click();
	}
	
	
	public void clickContactUs(){
		contactUsMenu.click();
	}
}
