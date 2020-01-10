package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommonFunLibrary.ERP_Functions;
import Utilities.ExcelFileUtils;

public class DataDriven {
WebDriver driver;
@BeforeTest
public void setUp() throws Throwable {
	String launch=ERP_Functions.launchApp("http://webapp.qedge.com");
	Reporter.log(launch, true);
	String login=ERP_Functions.verifyLogin("admin","master");
	Reporter.log(login, true);
}
@Test
public void supplierCreation() throws Throwable{
	ExcelFileUtils xl=new ExcelFileUtils();
	int rc=xl.rowCount("Supplier");
	int cc=xl.colCount("Supplier");
	Reporter.log("no of rows are::"+" "+rc+"  "+"no of columns are::"+" "+cc,true );
	for(int i=1;i<=rc;i++){
		String sname=xl.getCellData("Supplier",i,0);
		String address=xl.getCellData("Supplier",i,1);
		String city=xl.getCellData("Supplier",i,2);
		String country=xl.getCellData("Supplier",i,3);
		String cperson=xl.getCellData("Supplier",i,4);
		String pnumber=xl.getCellData("Supplier",i,5);
		String email=xl.getCellData("Supplier",i,6);
		String mnumber=xl.getCellData("Supplier",i,7);
		String notes=xl.getCellData("Supplier",i,8);
String results=ERP_Functions.verifySupplierCreation(sname, address, city, country, cperson, pnumber, email, mnumber, notes);
xl.setCellData("Supplier",i,9,results);
Reporter.log(results,true);
	}
	}	
@AfterTest
public void logout(){
	ERP_Functions.verifylogout();
}
}