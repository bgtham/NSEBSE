package com.nse.data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DataDownloadNSE {
	
	static Wait<WebDriver> wait;

	@SuppressWarnings({ "deprecation", "static-access" })
	public static void main(String[] args) throws InterruptedException, ParseException, IOException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.gecko.driver","C:\\Users\\gburle\\Desktop\\Stocks\\geckodriver.exe");
		
		String zipFilepath;
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("NSE");
		WebDriver driver = new FirefoxDriver(ffprofile);
	    
	    driver.navigate().to("https://www.nseindia.com/products/content/equities/equities/archieve_eq.htm");
	    wait = new WebDriverWait(driver, 2000);
	    
	    org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
	    LocalDate startDate = dtf.withLocale(Locale.UK).parseLocalDate("01-01-2017");
	    LocalDate endDate = dtf.withLocale(Locale.UK).parseLocalDate("30-04-2017");
		int Loop = Days.daysBetween(startDate, endDate).getDays();

		for (int i = 0; i <= Loop ; i++) {
			
			String[] DateToEnter;
			DateToEnter = startDate.toString().split("-");
			driver.navigate().refresh();
		    Thread.sleep(3000);
			driver.findElement(By.xpath("//select[@id='h_filetype']")).sendKeys("Bhav");
			driver.findElement(By.xpath("//input[@id='date']")).clear();
		    driver.findElement(By.xpath("//input[@id='date']")).sendKeys(DateToEnter[2]+"-"+DateToEnter[1]+"-"+DateToEnter[0]);
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//input[@id='date']")).sendKeys(Keys.TAB);
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//input[@class='getdata-button']")).click();
		    startDate = startDate.plusDays(1);
		    Thread.sleep(3000);
		    //wait.wait();
		    
		    try {
		    	zipFilepath = "C:\\NSEDailyData\\" + driver.findElement(By.xpath("//a[contains(.,'csv.zip')]")).getText();
		    	driver.findElement(By.xpath("//a[contains(.,'csv.zip')]")).click();
		    	Thread.sleep(3000);
		    	UnzipUtility.unzip(zipFilepath, "C:\\NSEDailyData\\");
		    } catch (NoSuchElementException e) {
		    	driver.findElement(By.xpath("//td[contains(.,'No file found for specified date. Try another date.')]"));
		    }

		}
		
		driver.close();
		driver.quit();
	}
}
