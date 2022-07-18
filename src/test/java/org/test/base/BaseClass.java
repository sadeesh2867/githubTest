package org.test.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorksheetDocument;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.asm.Advice.Enter;

public class BaseClass {
	 public static WebDriver driver;
	public static WebDriver ChromeLaunch() {
		
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		return driver;
	}
	public static WebDriver FirefoxLaunch() {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		return driver;
	}
	public static WebDriver EdgeLaunch() {
		WebDriverManager.edgedriver().setup();
		driver=new EdgeDriver();
		return driver;
	}
	public static WebDriver browserLaunch(String browserName) {
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			 driver=new ChromeDriver();
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			 driver =new EdgeDriver();
			
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			 driver =new FirefoxDriver();
			
		}
		return driver;
		

	}
	public static void url(String url) {
		driver.get(url);
	}
	public static void maximize() {
	driver.manage().window().maximize();
	}
	public static void time(long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

	}
	public static void sendKeys(WebElement e,String value) {
		e.sendKeys(value);
		
	}
	public static void click(WebElement e) {
		e.click();
	}
	public static String currentUrl() {
		String url = driver.getCurrentUrl();
		return url;	
	}
	public static String title() {
		String title = driver.getTitle();
		return title;
	}
	public static void moveToElement(WebElement e) {
		Actions a=new Actions(driver);
		a.moveToElement(e).perform();
	}
	public static void doubleClick(WebElement e) {
		Actions a=new Actions(driver);
		a.doubleClick(e).perform();

	}
	public static void contextClick(WebElement e) {
		Actions a=new Actions(driver);
		a.contextClick(e).perform();
	}
	public static void screenShot() throws IOException {
		long img = System.currentTimeMillis();
		TakesScreenshot ts=(TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File des=new File("C:\\Users\\kaila\\eclipse-workspace\\Selenium\\ScreenShot\\image"+img+".png");
		FileUtils.copyFile(src, des);
		
	}
	public static void Alertt() {
		Alert alrt = driver.switchTo().alert();
		alrt.accept();
		//alrt.dismiss();

	}
	public static void quit() {
		driver.quit();

	}
	public static void close() {
		driver.close();

	}
	public static void keydown(int e) throws AWTException {
		Robot r=new Robot();
		for(int i=0;i<e;i++) {
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);

	}
	}
	public void keyEnter() throws AWTException {
		Robot r=new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

	}
	public static void scrollDown(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", e);

	}
	public static void scrollUp(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", e);
	}
	public static void javaScriptSenkeys(WebElement e ,String value) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value','"+value+"')", e);	
	}
	public static void javaScriptClick(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", e);		
	}
	public static void sendKeyWithEnter(WebElement e,String value) {
		e.sendKeys(value,Keys.ENTER);
		
	}
	public static String windosHandling(int i) {
		String parentId = driver.getWindowHandle();
		Set<String> allId = driver.getWindowHandles();
		List <String>li=new ArrayList();
		li.addAll(allId);
		driver.switchTo().window(li.get(i));
		return parentId;
		
	}
	public static String getText(WebElement e) {
		 String text = e.getText();
		return text;
		

	}
	public static String getExcel(String fileName,String sheetName,int row,int cell) throws IOException {
		String value;
		File f=new  File("C:\\Users\\kaila\\eclipse-workspace\\Maven\\src\\test\\resources\\"+fileName+".xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook w=new XSSFWorkbook(fi);
		Sheet s = w.getSheet(sheetName);
		Row r = s.getRow(row);
		Cell c = r.getCell(cell);
		int type = c.getCellType();
		if(type==1) {
			 value = c.getStringCellValue();
			
		}
		else {
			if(DateUtil.isCellDateFormatted(c)) {
				Date d = c.getDateCellValue();
				SimpleDateFormat s1=new SimpleDateFormat("dd/MM/YYYY");
				 value = s1.format(d);
			} else {
				double d = c.getNumericCellValue();
				long l=(long) d;
				 value = String.valueOf(l);
				
			}
		}
		return value;
	

	} 
	public static void main(String[] args) {
		FirefoxLaunch();
	}
	
	
}
	
	
	
	
	


	


