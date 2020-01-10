package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
static WebDriver driver;
//Method for launching browser
public static  WebDriver startBrowser() throws Throwable{
	if(PropertyFileUtil.getVAlueForKey("Browser").equalsIgnoreCase("chrome")){
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium_Evening\\ERP_Stock\\CommonDrivers\\chromedriver.exe");
		driver=new ChromeDriver();	
	}
	else if(PropertyFileUtil.getVAlueForKey("Browser").equalsIgnoreCase("firefox"))
	{
		System.setProperty("webdriver.gecko.driver", "D:\\Selenium_Evening\\ERP_Stock\\CommonDrivers\\geckodriver.exe");
		driver=new FirefoxDriver();
	}
	return driver;
}
public static void openApplication(WebDriver driver) throws Throwable
{
driver.get(PropertyFileUtil.getVAlueForKey("Url"));
driver.manage().window().maximize();
}
public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String timetowait){
WebDriverWait wait=new WebDriverWait(driver, Integer.parseInt(timetowait));
if(locatortype.equalsIgnoreCase("id")){
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
}
else if(locatortype.equalsIgnoreCase("name"))
{
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
}
else if(locatortype.equalsIgnoreCase("xpath")){
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
}
}
public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata){
	if(locatortype.equalsIgnoreCase("id")){
		driver.findElement(By.id(locatorvalue)).clear();
		driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).clear();
		driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).clear();
		driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
	}
}
public static void clickAction(WebDriver driver,String locatortype,String locatorvalue){
	if(locatortype.equalsIgnoreCase("id")){
		driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).sendKeys(Keys.ENTER);
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).click();
	}	
}
public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable{
	String snumber="";
	if(locatortype.equalsIgnoreCase("id"))
	{
		snumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");	
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		snumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		snumber=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
	}
	//write supplier number into notepad
	FileWriter fw=new FileWriter("D:\\Selenium_Evening\\ERP_Stock\\CaptureData\\Supplier.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(snumber);
	bw.flush();
	bw.close();
}
public static void tableValidation(WebDriver driver,String testdata) throws Throwable
{
FileReader fr=new FileReader("D:\\Selenium_Evening\\ERP_Stock\\CaptureData\\supplier.txt");
BufferedReader br=new BufferedReader(fr);
String expected_data=br.readLine();
//convert coldata into string
int column=Integer.parseInt(testdata);
if(!driver.findElement(By.xpath(PropertyFileUtil.getVAlueForKey("Search-Box"))).isDisplayed())
{
	driver.findElement(By.xpath(PropertyFileUtil.getVAlueForKey("Search-Panel"))).click();
	}
else
{
	//Do Nothing
}
Thread.sleep(2000);
driver.findElement(By.xpath(PropertyFileUtil.getVAlueForKey("Search-Box"))).clear();
Thread.sleep(2000);
driver.findElement(By.xpath(PropertyFileUtil.getVAlueForKey("Search-Box"))).sendKeys(expected_data);
Thread.sleep(2000);
driver.findElement(By.xpath(PropertyFileUtil.getVAlueForKey("Search-Button"))).click();
WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getVAlueForKey("Supp-Table")));
List<WebElement> rows=table.findElements(By.tagName("tr"));
for(int i=1;i<rows.size();i++)
{
	String actual_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
Thread.sleep(2000);
System.out.println(expected_data+"  "+actual_data);
Assert.assertEquals(actual_data, expected_data,"Snumber Is Not Matching");
break;
}

}

public static String generateDate(){
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_DD_hh_mm_ss");
	return sdf.format(date);
}
public static void closeBrowser(WebDriver driver){
	driver.quit();
}
}