package com.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelManager {
	
	HSSFRow row;
	HSSFCell cell;
	
	public String getRunMode(String _TestCaseName) {
		String _RunMode = new String();
		String path = "./TestManager.xls";
		_RunMode = getCurrentTestData(path, 0, _TestCaseName).get("Run");
		return _RunMode;
		
		
	}
	
	private Hashtable<String, Integer> getWorkSheetColumnIndex(String path, int sheetNumber){
		HSSFWorkbook workbook = getWorkBook(path);
		String sheetName = workbook.getSheetName(sheetNumber);
		return getWorkSheetColumnIndex(path, sheetName);
		
	}
	
	
	private Hashtable<String, Integer> getWorkSheetColumnIndex(String path, String sheetname){
		Hashtable<String, Integer> columns = new Hashtable<String, Integer>();
		HSSFWorkbook workbook = getWorkBook(path);
		HSSFSheet sheet = workbook.getSheet(sheetname);
		int lastColumns = sheet.getRow(0).getLastCellNum();
		String colName= "";
		for (int i = 0; i <= lastColumns; i++) {
			
			cell = sheet.getRow(0).getCell(i);
			if(cell !=null) {
				colName = cell.getStringCellValue();
				columns.put(colName, i);
			}
		}
		
		return columns;
	}
	
	
	private HSSFWorkbook getWorkBook(String filePaht) {
		FileInputStream fis = null;
		HSSFWorkbook workbook  = null;
		try {
			fis = new FileInputStream(filePaht);
			workbook = new HSSFWorkbook(fis);
		} catch (IOException e) {
			return null;
		}
		return workbook;
	}
	
	private Hashtable<String, String> getCurrentTestData(String path, int sheetNumber, String testCaseName){
		HSSFWorkbook workbook = getWorkBook(path);
		String sheetName = workbook.getSheetName(sheetNumber);
		return getCurrentTestData( path, sheetName, testCaseName);
		
	}
	
	

	public Hashtable<String, String> getCurrentTestData(String path, String sheetName, String testCaseName){
				Hashtable<String, String> testData = new Hashtable<String, String>();
				HSSFWorkbook workbook = getWorkBook(path);
				HSSFSheet sheet = workbook.getSheet(sheetName);
				int lastColumns = sheet.getRow(0).getLastCellNum();
				int lastRow = sheet.getLastRowNum();
				String columnName="";
				String columnData="";
				int testcaseidColNum = getWorkSheetColumnIndex(path, sheetName).get("Test Case");
				boolean testCaseFoundStatus = false;
				for (int i = 0; i <= lastRow; i++) {
					row = sheet.getRow(i);
					if(row!=null) {
						if(testCaseName.equals(row.getCell(testcaseidColNum).getStringCellValue())) {
							testCaseFoundStatus = true;
							for (int j = 0; j <= lastColumns; j++) {
								cell = row.getCell(j);
								if(cell!=null) {
									columnName = sheet.getRow(0).getCell(j).getStringCellValue();
									columnData = row.getCell(j).getStringCellValue();
									testData.put(columnName, columnData);
								}
							}
						}
					}
					if(testCaseFoundStatus) {
						break;
					}
				}
	
	return testData;
	}
	
	
	public HSSFRow getCurrentTestRow(String path, String sheetName, String testCaseName){
		
		HSSFWorkbook workbook = getWorkBook(path);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		int lastRow = sheet.getLastRowNum();
		int testcaseidColNum = getWorkSheetColumnIndex(path, sheetName).get("Test Case");
		for (int i = 0; i <= lastRow; i++) {
			row = sheet.getRow(i);
			if(row!=null) {
				if(testCaseName.equals(row.getCell(testcaseidColNum).getStringCellValue())) {
					return row;
				}
			}
		}

		return row;
}

	
	public void updateTestStatusInTestManager(String testCaseName, String status) {
		String path = "./TestManager.xls";
		HSSFWorkbook workbook = getWorkBook(path);
		String sheetname = workbook.getSheetName(0);
		int statusColNum = getWorkSheetColumnIndex(path, sheetname).get("Status");
		row = getCurrentTestRow(path, sheetname, testCaseName);
		cell = row.getCell(statusColNum);
		if(cell!=null) {
			row.getCell(statusColNum).setCellValue(status);
		}else {
			row.createCell(statusColNum).setCellValue(status);
		}
		workbook = row.getSheet().getWorkbook();
		saveWorkBook(path, workbook);
		
	}
	
	
	public void saveWorkBook(String path, HSSFWorkbook workbook) {
		FileOutputStream fos =null;
		try {
			fos = new FileOutputStream(path);
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		try {
			fos.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		
	}
}
