package me.t.kaurami.service.bookReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("standardBookReader")
public class StandardBookReader implements BookReader {
    private Workbook workbook;
    private int selectedSheetNumber = 0; //default first sheet
    private List<String> sheetsNames;
    private Sheet sheet;
    private Row row;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void loadBook(File file) throws IOException, InvalidFormatException {
        if (file.getName().contains(".xls")){
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }else if (file.getName().contains(".xlsx")){
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }else {
            throw new UnsupportedOperationException("File is not valid");
        }
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        sheetsNames = new ArrayList<String>();
        while (sheetIterator.hasNext()){
            sheetsNames.add(sheetIterator.next().getSheetName());
        }
    }

    @Override
    public void setSelectedSheetNumber(int selectedSheetNumber){
        this.selectedSheetNumber = selectedSheetNumber;
    }

    @Override
    public void setSelectedSheetNumber(String sheetName){
        if (workbook==null){
            throw new IllegalArgumentException("Workbook is not defined");
        }
        this.selectedSheetNumber = workbook.getSheetIndex(sheetName);
    }

    @Override
    public void setDateFormat(String pattern){
        dateFormat.applyPattern(pattern);
    }

    @Override
    public List<String> getSheetsNames() {
        return sheetsNames;
    }

    @Override
    public LinkedList<LinkedList<String>> readSheet(){
        setSheet();
        LinkedList<LinkedList<String>> rowsValues = new LinkedList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++){
            row = sheet.getRow(i);
            LinkedList<String> cellsValues = new LinkedList<>();
            for (int j = 0; j < row.getLastCellNum(); j++){
                cellsValues.add(extractStringCellValue(row.getCell(j)));
            }
            rowsValues.add(cellsValues);
        }
        return rowsValues;
    }

    private void setSheet(){
        sheet = workbook.getSheetAt(selectedSheetNumber);
    }

    private String extractStringCellValue(Cell cell){
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    return dateFormat.format(cell.getDateCellValue());
                }else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    @Override
    @Deprecated
    public Workbook getWorkbook() {
        return workbook;
    }
}