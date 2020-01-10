package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {
Workbook wb;
Sheet ws;
Row rownum;
Cell cell;
CellStyle style;	
	//Constructor for reading excel path
	public ExcelFileUtils() throws Throwable{
		FileInputStream fi=new FileInputStream("D:\\Selenium_Evening\\ERP_Stock\\TestInput\\InputSheet.xlsx");
		wb=WorkbookFactory.create(fi);	
	}
	
	//counting no of rows in a sheet
public int rowCount(String sheetname){
	return wb.getSheet(sheetname).getLastRowNum();
}
public int colCount(String sheetname){
	return wb.getSheet(sheetname).getRow(0).getLastCellNum();
}
@SuppressWarnings("deprecation")
public String getCellData(String sheetname,int row,int column){
	String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC){
		int celldata= (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		data=String.valueOf(celldata);
	}else{
		data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
return data;
}
public void setCellData(String sheetname,int row,int column,String status) throws Throwable{
	ws=wb.getSheet(sheetname);
	rownum=ws.getRow(row);
	cell=rownum.createCell(column);
	cell=rownum.getCell(column);
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass")){
		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	Font font=wb.createFont();
	font.setBold(true);
	style.setFont(font);
	cell.setCellStyle(style);
	}else if(status.equalsIgnoreCase("Fail")){
		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	Font font=wb.createFont();
	font.setBold(true);
	style.setFont(font);
	cell.setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("blocked")){
		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	Font font=wb.createFont();
	font.setBold(true);
	style.setFont(font);
	cell.setCellStyle(style);
	}
	FileOutputStream fo=new FileOutputStream("D:\\Selenium_Evening\\ERP_Stock\\TestOutput\\Hybrid.xlsx");
	wb.write(fo);
	fo.close();
    }

    } 

