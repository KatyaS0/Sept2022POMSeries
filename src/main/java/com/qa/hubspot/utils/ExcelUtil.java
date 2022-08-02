package com.qa.hubspot.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openxml4j.exceptions.InvalidFormatException;

public class ExcelUtil {
	
	//we have to add dependecies for apache POI APA
	
	private static Workbook book;
	private static Sheet sheet;

	
	public static String TEST_DATA_SHEET_PATH = "./July2022POMSeries/src/main/java/com/qa/hubspot/testdata/Untitled spreadsheet (1).xlsx";
	
	public static Object[][] getTestData(String sheetName) {		//from which sheet do you want to fetch the data?
		Object data [][] = null;
		
	try {
		FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH); //this will give the path of the xml file. We will maintain it in the class level 
		book = WorkbookFactory.create(ip); //does not find any method as such. There is a method XSSFWorkBookfactory but idk if thats the one 
		//this will create excat reolica of the exel sheet 
		sheet = book.getSheet(sheetName);
		
		data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()]; //this is an empty data for now
		//now we need to fill the data
		
		for (int i=0; i<sheet.getLastRowNum(); i++) {
			for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i+1).getCell(j).toString(); 
					
			}
		}
		
		
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (InvalidFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return data;
		
	}
}
