package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static final String FILE_PATH = "src/test/resources/TestData.xlsx";
	private static final String SHEET_NAME = "TestData";
	
	
	public static Map<String,String> getTestData(String testName){
		Map<String,String> data = new HashMap<>();
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			
			Workbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheet(SHEET_NAME);
			Row header = sheet.getRow(0);

			String key = "";
			String value = "";
			
			for(Row row:sheet) {
				if(row.getCell(0).getStringCellValue().equalsIgnoreCase(testName)) {
					for(int i=1;i<row.getLastCellNum();i++) {
						key = header.getCell(i).toString();
						try {
							value = row.getCell(i).toString();
						}catch(NullPointerException e) {
							value = "";
						}
						
						data.put(key, value);
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void updateResult(String testName, String status, String remarks){
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			
			Workbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheet(SHEET_NAME);
			
			for(Row row:sheet) {
				if(row.getCell(0).getStringCellValue().equalsIgnoreCase(testName)) {
					row.createCell(2).setCellValue(status);
					row.createCell(3).setCellValue(remarks);
					break;
				}
			}
			
			try(FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
				wb.write(fos);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
}