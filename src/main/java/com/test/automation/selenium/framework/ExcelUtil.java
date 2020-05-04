package com.test.automation.selenium.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	// private  String FileName;
	private  XSSFSheet ExcelWSheet;
	private  XSSFWorkbook ExcelWBook;
	private  XSSFCell Cell;
	private  XSSFRow Row;

	public synchronized  int getRowNumber(String xlsFileName, String SheetName) throws Exception {
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ZipSecureFile.setMinInflateRatio(-1.0d);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			return ExcelWBook.getSheet(SheetName).getLastRowNum();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public synchronized  int getRowNumber(InputStream inputStream, String SheetName) throws Exception {
		try {
			ExcelWBook = new XSSFWorkbook(inputStream);
			return ExcelWBook.getSheet(SheetName).getLastRowNum();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public synchronized  String getDataFromCell() {
		String Data;
		FormulaEvaluator evaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();
		switch (evaluator.evaluateInCell(Cell).getCellType()) {
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
			Data = String.valueOf(Cell.getNumericCellValue());
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
			Data = Cell.getStringCellValue();
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
			Data = "";
			break;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR:
			Data = String.valueOf(Cell.getErrorCellValue());
			break;
		default:
			Data = Cell.getStringCellValue();
		}
		return Data;

	}

	public synchronized  String getCellData(String SheetName, int RowNum, int ColNum) throws Exception {
		String CellData;
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			CellData = getDataFromCell();
			ExcelFile.close();
			return CellData;
		} catch (Exception e) {
			e.printStackTrace();
			ExcelFile.close();
			return "";
		}
	}

	public synchronized  String getCellData(String SheetName, int RowNum, String ColName) throws Exception {
		String CellData = null;
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWBook.setForceFormulaRecalculation(true);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int noOfCOl = ExcelWSheet.getRow(0).getLastCellNum();
			for (int ii = 0; ii < noOfCOl; ii++) {
				Cell = ExcelWSheet.getRow(0).getCell(ii);
				CellData = Cell.getStringCellValue();
				if (CellData.toLowerCase().equalsIgnoreCase(ColName)) {
					Cell = ExcelWSheet.getRow(RowNum).getCell(ii);
					CellData = getDataFromCell();
					ExcelFile.close();
					return CellData;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExcelFile.close();
			return "";
		}
		return "";
	}

	public synchronized  String getCellData(String xlsFileName, String SheetName, int RowNum, int ColNum) throws Exception {
		String CellData;
		FileInputStream ExcelFile = null;
		System.out.println("File: " + xlsFileName +"Sheet: " + SheetName + "Row: " + RowNum + "Col: " + ColNum );
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWBook.setForceFormulaRecalculation(true);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (Cell != null) {
				CellData = getDataFromCell();
			} else {
				CellData = "";
			}
			ExcelFile.close();
			return CellData;
		} catch (Exception e) {
			e.printStackTrace();
			ExcelFile.close();
			return "";
		}
	}

	public synchronized  String getCellData(String xlsFileName, String SheetName, int RowNum, String ColName)
			throws Exception {
		String CellData = null;
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWBook.setForceFormulaRecalculation(true);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int noOfCOl = ExcelWSheet.getRow(0).getLastCellNum();
			for (int ii = 0; ii < noOfCOl; ii++) {
				Cell = ExcelWSheet.getRow(0).getCell(ii);
				CellData = Cell.getStringCellValue();
				if (CellData.toLowerCase().equalsIgnoreCase(ColName)) {
					Cell = ExcelWSheet.getRow(RowNum).getCell(ii);
					CellData = getDataFromCell();
					ExcelFile.close();
					return CellData;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExcelFile.close();
			return "";
		}
		return "";
	}

	public synchronized  int GetRowIndexByData(String xlsFileName, String SheetName, int ColNum, String Data)
			throws Exception {
		String CellData;
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int iRowNumber = ExcelWSheet.getLastRowNum();
			for (int ii = 1; ii < iRowNumber; ii++) {
				Cell = ExcelWSheet.getRow(ii).getCell(ColNum);
				CellData = Cell.getStringCellValue();
				if (Data.toLowerCase().equalsIgnoreCase(CellData)) {
					ExcelFile.close();
					return ii;
				}
			}
			ExcelFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			ExcelFile.close();
			return -1;
		}
		return -1;
	}

	public synchronized  int GetRowIndexByData(String SheetName, int ColNum, String Data) throws Exception {
		String CellData;
		FileInputStream ExcelFile = null;
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int iRowNumber = ExcelWSheet.getLastRowNum();
			for (int ii = 1; ii < iRowNumber; ii++) {
				Cell = ExcelWSheet.getRow(ii).getCell(ColNum);
				CellData = Cell.getStringCellValue();
				if (Data.toLowerCase().equalsIgnoreCase(CellData)) {
					ExcelFile.close();
					return ii;
				}
			}
			ExcelFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			ExcelFile.close();
			return -1;
		}
		return -1;
	}

	public synchronized  void setCellData(String xlsFileName, String SheetName, String Result, int RowNum, int ColNum)
			throws Exception {
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(xlsFileName);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			ExcelFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ExcelFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			ExcelFile.close();
		}
	}

	public synchronized  void setCellDataForMultipleRows(String xlsFileName, String SheetName, String Result, String rowRange,
			int ColNum) throws Exception {
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			String arrRowRange[] = rowRange.split("-");
			for (int ii = Integer.parseInt(arrRowRange[0]); ii < Integer.parseInt(arrRowRange[1]); ii++) {
				Row = ExcelWSheet.getRow(ii);
				Cell = Row.getCell(ColNum);
				if (Cell == null) {
					Cell = Row.createCell(ColNum);
					Cell.setCellValue(Result);
				} else {
					Cell.setCellValue(Result);
				}
			}
			FileOutputStream fileOut = new FileOutputStream(xlsFileName);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			ExcelFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ExcelFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			ExcelFile.close();
		}
	}

	public synchronized  void setCellDataForMultipleColumns(String xlsFileName, String SheetName, String value, int rowNum,
			String colRange) throws Exception {
		FileInputStream ExcelFile = null;
		int lastColumn = 0;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			String arrRowRange[] = colRange.split("-");
			lastColumn = Integer.parseInt(arrRowRange[1]);
			for (int ii = Integer.parseInt(arrRowRange[0]); ii < lastColumn; ii++) {
				Row = ExcelWSheet.getRow(rowNum);
				Cell = Row.getCell(ii);
				if (Cell == null) {
					Cell = Row.createCell(ii);
					Cell.setCellValue(value);
				} else {
					Cell.setCellValue(value);
				}
			}
			FileOutputStream fileOut = new FileOutputStream(xlsFileName);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			ExcelFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ExcelFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			ExcelFile.close();
		}
	}

	public synchronized  void storeCellData(String SheetName, String Result, int RowNum, int ColNum) {
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		try {
			setCellData(xlsFileName, SheetName, Result, RowNum, ColNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized  void storeCellDataForMultipleRows(String SheetName, String Result, String RowRange, int ColNum) {
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		try {
			setCellDataForMultipleRows(xlsFileName, SheetName, Result, RowRange, ColNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized  void storeCellDataForMultipleColumns(String SheetName, String Result, int rowNum, String colRange) {
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		try {
			setCellDataForMultipleColumns(xlsFileName, SheetName, Result, rowNum, colRange);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized  void storeCellData(String SheetName, String Result, int RowNum, String ColName) {
		FileInputStream ExcelFile = null;
		String CellData;
		String[] NameLists = SheetName.split("_");
		String xlsFileName = System.getProperty("user.dir") + "//" + Environment.dataDir + "//" + NameLists[0] + ".xlsx";;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWBook.setForceFormulaRecalculation(true);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int noOfCOl = ExcelWSheet.getRow(0).getLastCellNum();
			for (int ii = 0; ii < noOfCOl; ii++) {
				Cell = ExcelWSheet.getRow(0).getCell(ii);
				CellData = Cell.getStringCellValue();
				if (CellData.toLowerCase().equalsIgnoreCase(ColName)) {
					setCellData(xlsFileName, SheetName, Result, RowNum, ii);
					return;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized  int GetNumberOfColumn(String xlsFileName, String SheetName) throws Exception {
		int noOfCol;
		FileInputStream ExcelFile = null;
		try {
			ExcelFile = new FileInputStream(xlsFileName);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			noOfCol = ExcelWSheet.getRow(0).getLastCellNum();
			ExcelFile.close();
			return noOfCol;
		} catch (Exception e) {
			e.printStackTrace();
			
			ExcelFile.close();
			return 0;
		}
	}

}
