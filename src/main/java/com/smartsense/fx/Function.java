package com.smartsense.fx;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Function {

	final static Logger logger = Logger.getLogger(Function.class);

	public static void dropDown(WebDriver driver, int row, int cellno, String sheetName) throws Exception {
		List<WebElement> elementList = driver.findElements(By.tagName("option"));
		System.out.println(elementList.size());
		String str = ExcelUtils.getCellData(row, cellno, sheetName);
		boolean check = false;

		for (int i = 0; i < elementList.size(); i++) {

			if (elementList.get(i).getText().equals(str)) {
				logger.info("Entering for loop");
				WebElement elementClick = elementList.get(i);
				Thread.sleep(5000);
				elementClick.click();
				check = true;
				break;
			}

		}
		if (!check) {
			elementList.get(1).click();
		}

	}

	public static void loginCredentials(WebDriver driver, int row) throws Exception {
		// username or email
		driver.findElement(By.xpath("//input[@type='text']"))
				.sendKeys(ExcelUtils.getCellData(row, 1, StringPool.MAIN_SHEET));

		// password
		driver.findElement(By.xpath("//input[@type='password']"))
				.sendKeys(ExcelUtils.getCellData(row, 2, StringPool.MAIN_SHEET));

		// click on the login button
		driver.findElement(By.xpath("//button[contains(text(),'LOGIN')]")).click();
		Thread.sleep(2000);

	}

	public static void dropDownTradingGuide(WebDriver driver, int row, int cell, String sheetname)
			throws Exception {
	

		List<WebElement> elementList = driver.findElements(By.tagName("md-option"));
		String str = ExcelUtils.getCellData(row, cell, StringPool.MAIN_SHEET);
		boolean check = false;

		for (int i = 0; i < elementList.size(); i++) {

			if (elementList.get(i).getText().contains(str)) {
				logger.info("Entering for loop");
				WebElement elementClick = elementList.get(i);
				Thread.sleep(5000);
				elementClick.click();
				check = true;
				break;
			}

		}
		if (!check) {
			elementList.get(1).click();
		}

	}

}
