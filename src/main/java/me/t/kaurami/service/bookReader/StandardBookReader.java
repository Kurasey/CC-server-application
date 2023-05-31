package me.t.kaurami.service.bookReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Basic implementation of BookReader
 */
@Service("standardBookReader")
@RequestScope
public class StandardBookReader implements BookReader {

    private static Logger logger = LoggerFactory.getLogger(BookReader.class);
    private Workbook workbook;
    private int selectedSheetNumber = 0; //default first sheet
    private List<String> sheetsNames;
    private Sheet sheet;
    private Row row;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void loadBook(File file) throws IOException, NotValidWorkbookException {
        logger.info("loading workbook...");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            if (file.getName().contains(".xlsx")){
                logger.info("workbook format is .xlsx");
                workbook = new XSSFWorkbook(inputStream);
            }else if (file.getName().contains(".xls")){
                logger.info("workbook format is .xls");
                workbook = new HSSFWorkbook(inputStream);
            }else {
                logger.error("workbook's format is not valid");
                throw new NotValidWorkbookException("Не допустимое значение! \nФайл должен иметь формат .xls или .xlsx");
            }
            logger.info("workbook loaded");
        }
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        sheetsNames = new ArrayList<String>();
        while (sheetIterator.hasNext()){
            sheetsNames.add(sheetIterator.next().getSheetName());
        }
        logger.info(String.format("workbook sheet count = %d", sheetsNames.size()));
    }

    @Override
    public void setSelectedSheetNumber(int selectedSheetNumber){
        if (workbook==null){
            throw new NullPointerException("Workbook is not defined");
        }
        this.selectedSheetNumber = selectedSheetNumber;
        logger.info(String.format("selected sheet %d", selectedSheetNumber));
    }

    @Override
    public void setSelectedSheetNumber(String sheetName){
        if (workbook==null){
            throw new NullPointerException("Workbook is not defined");
        }
        this.selectedSheetNumber = workbook.getSheetIndex(sheetName);
        logger.info(String.format("selected sheet %d", sheetName));
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
        logger.info("reading sheet");
        setSheet();
        LinkedList<LinkedList<String>> rowsValues = new LinkedList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++){
            row = sheet.getRow(i);
            LinkedList<String> cellsValues = new LinkedList<>();
            for (int j = 0; j < row.getLastCellNum(); j++){
/*                try {
                    cellsValues.add(extractStringCellValue(row.getCell(j)));
                }catch (NullPointerException e){
                    cellsValues.add("");
                }*/
                try {
                    cellsValues.add(extractStringCellValue(row.getCell(j)));
                }catch (Exception e){
                    logger.error(String.format("Error extracting a string value from row '%d' cell '%d'. %s", i, j, e.toString()));
                    cellsValues.add(null);
                }
            }
            rowsValues.add(cellsValues);
        }
        logger.info("sheet is read");
        return rowsValues;
    }

    private void setSheet(){
        sheet = workbook.getSheetAt(selectedSheetNumber);
        logger.info(String.format("selected sheet: %d", selectedSheetNumber));
    }

    /**
     * Extract cell value as String
     * @param cell cell of workbook
     * @return cell value as String
     * @throws NullPointerException
     */
    private String extractStringCellValue(Cell cell) throws NullPointerException{
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    return dateFormat.format(cell.getDateCellValue());
                }else {
//                    String value = formatter.formatCellValue(cell);
//
//                    System.out.println(value);
//                     return value;
                    return (Double.valueOf(cell.getNumericCellValue())).toString();
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