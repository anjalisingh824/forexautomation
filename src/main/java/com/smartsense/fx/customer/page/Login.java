package com.smartsense.fx.customer.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
	
	private WebDriver driver;
	public Login(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//button[contains(text(),'LOGIN')]")
	public WebElement loginBtn;
	
	@FindBy(xpath="//input[@type='text']")
	public WebElement loginByEmail;
	
	@FindBy(xpath="//input[@type='password']")
	public WebElement passwordEnter;
	
	
	
//	public WebElement getLoginBtn() {
//		return loginBtn;
//	}
//
//	public void setLoginBtn(WebElement loginBtn) {
//		this.loginBtn = loginBtn;
//	}
//	
//	public WebElement getLoginByEmail()
//	{
//		return loginByEmail;
//	}
//	
//	public void setLoginByEmail(WebElement loginByEmail)
//	{
//		this.loginByEmail=loginByEmail;
//	}
//	
//	public WebElement getPasswordEnter()
//	{
//		return passwordEnter;
//	}
//	
//	public void setPasswordEnter(WebElement passwordEnter)
//	{
//		this.passwordEnter=passwordEnter;
//	}
//	
	public void clickLogin(){
		loginBtn.click();
	}
	
	
}
