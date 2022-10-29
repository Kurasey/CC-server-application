package me.t.kaurami.service.setting;

import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReportFileParameters {         //rename
    private List<String> columnsReport;
    private LinkedList<String> fields;
    private List<Integer> numericFormat;
    private List<Integer> valueToNumeric;
    private String sheetName;
    private HashMap<String, Integer> mathingFieldsAndColumns;
    private HashMap<String, CellStyle> styles;

    public List<Integer> getValueToNumeric() {
        return valueToNumeric;
    }

    public void setValueToNumeric(List<Integer> valueToNumeric) {
        this.valueToNumeric = valueToNumeric;
    }

    public HashMap<String, Integer> getMathingFieldsAndColumns() {
        return mathingFieldsAndColumns;
    }

    public void setMathingFieldsAndColumns(HashMap<String, Integer> mathingFieldsAndColumns) {
        this.mathingFieldsAndColumns = mathingFieldsAndColumns;
    }

    public void setColumnsReport(List<String> columnsReport) {
        this.columnsReport = columnsReport;
    }

    public void setFields(LinkedList<String> fields) {
        this.fields = fields;
    }

    public void setNumericFormat(List<Integer> numericFormat) {
        this.numericFormat = numericFormat;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<String> getColumnsReport() {
        return columnsReport;
    }

    public LinkedList<String> getFields() {
        return fields;
    }

    public List<Integer> getNumericFormat() {
        return numericFormat;
    }

    public String getSheetName() {
        return sheetName;
    }

    public HashMap<String, CellStyle> getStyles() {
        return styles;
    }

    public void createStyles(Workbook workbook){
        CellStyle styleTitle = workbook.createCellStyle();
        CellStyle styleText = workbook.createCellStyle();
        CellStyle styleNumber = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)8.5);
        font.setFontName("MS Sans Serif");
        styleTitle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitle.setBorderTop(BorderStyle.MEDIUM);
        styleTitle.setBorderRight(BorderStyle.MEDIUM);
        styleTitle.setBorderBottom(BorderStyle.MEDIUM);
        styleTitle.setBorderLeft(BorderStyle.THIN);
        styleTitle.setFont(font);

        styleText.setBorderLeft(BorderStyle.THIN);
        styleText.setBorderRight(BorderStyle.THIN);
        styleText.setBorderBottom(BorderStyle.THIN);
        styleText.setFont(font);
        styleText.setDataFormat(workbook.createDataFormat().getFormat("#"));

        styleNumber.setBorderLeft(BorderStyle.THIN);
        styleNumber.setBorderRight(BorderStyle.THIN);
        styleNumber.setBorderBottom(BorderStyle.THIN);
        styleNumber.setFont(font);
        styleNumber.setDataFormat(workbook.createDataFormat().getFormat("# ##0"));

        styles = new HashMap<String, CellStyle>();
        styles.put("Title", styleTitle);
        styles.put("Text", styleText);
        styles.put("Number", styleNumber);
    }
}
