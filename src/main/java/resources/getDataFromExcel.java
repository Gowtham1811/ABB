package resources;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getDataFromExcel {
    private Workbook workbook;

    public getDataFromExcel(String excelFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        workbook = new XSSFWorkbook(fileInputStream);
    }

    public List<List<String>> getSheetData(String sheetName) {
        List<List<String>> sheetData = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        rowData.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            rowData.add(cell.getDateCellValue().toString());
                        } else {
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                    case BOOLEAN:
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case FORMULA:
                        rowData.add(cell.getCellFormula());
                        break;
                    default:
                        rowData.add("");
                }
            }
            sheetData.add(rowData);
        }
        return sheetData;
    }
    public static void validateData(List<List<String>> dataFromExcel, ArrayList<String> dataFromUI, String dataToCheck) {
		for(int i=0,j=0;i<dataFromExcel.size();i++,j=0) {
			if(dataFromExcel.get(i).get(j).toString().equalsIgnoreCase(dataToCheck)) {
				int k=0;j++;
        		for(;j<dataFromExcel.get(i).size();j++) {
        			if(dataFromExcel.get(i).get(j).toString().contentEquals(dataFromUI.get(k))){
        				k++;
        			}
        			else {
        				Assert.fail("List View Value " +dataFromUI.get(k)+ " not matching with " +dataFromExcel.get(i).get(j).toString());
        			}
			}
		}
		}
    }
    public static ArrayList<String> getData(List<List<String>> dataFromExcel, String dataToCheck) {
 		ArrayList<String> testData = new ArrayList<String>();
		for(int i=0,j=0;i<dataFromExcel.size();i++,j=0) {
 			if(dataFromExcel.get(i).get(j).toString().equalsIgnoreCase(dataToCheck)) {
 				j++;
         		for(;j<dataFromExcel.get(i).size();j++) {
         			testData.add(dataFromExcel.get(i).get(j).toString());
 			}
 		}
 		}
 		return testData;
     }
    public static ArrayList<String> getData2(List<List<String>> dataFromExcel) {
 		ArrayList<String> testData = new ArrayList<String>();
		for(int i=0,j=0;i<dataFromExcel.size();i++,j=0) {
         		for(;j<dataFromExcel.get(i).size();j++) {
         			testData.add(dataFromExcel.get(i).get(j).toString());
 		}
 		}
 		return testData;
     }
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
