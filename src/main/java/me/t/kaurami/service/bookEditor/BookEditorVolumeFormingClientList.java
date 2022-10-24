package me.t.kaurami.service.bookEditor;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.setting.ReportSetting;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("bookEditor")
public class BookEditorVolumeFormingClientList implements BookEditor {
    private Workbook workbook;
    private Sheet sheet;
    private Row row;
    private Cell cell;
    private ReportSetting setting;

    @Override
    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void setSetting(ReportSetting setting) {
        this.setting = setting;
    }

    @Override
    public void writeReport(List<Exportable> report/*refactor to interface*/){
        if (workbook==null){
            workbook = new XSSFWorkbook();
        }
        sheet = workbook.createSheet(setting.getSheetName());
        int rowNum = 1;
        int cellNum;
        HashMap<String, String> values;
        for (Exportable exportable: report){
            values = exportable.toExport();
            cellNum = 0;                                                            //? not use
            row = sheet.createRow(rowNum++);
            for (int i = 0; i<=setting.getColumnsReport().size()-1; i++){
                row.createCell(i);
            }
            for (Map.Entry entry: setting.getMathingFieldsAndColumns().entrySet()){
                if (setting.getValueToNumeric().contains(entry.getValue())){
                    row.getCell((int)entry.getValue()).setCellValue(toNumeric(values.get(entry.getKey())));
                }else{
                    row.getCell((int)entry.getValue()).setCellValue(values.get(entry.getKey()));
                }
            }
        }
        editReportSheet();
    }

    private void editReportSheet(){
        setting.createStyles(workbook);
        row = sheet.createRow(0);
        int cellNum = 0;
        for (String cellValue: setting.getColumnsReport()){
            cell = row.createCell(cellNum++);
            cell.setCellValue(cellValue);
            cell.setCellStyle(setting.getStyles().get("Title"));
        }
        List<Integer> numerics = setting.getNumericFormat();
        for (int i = 1; i<= sheet.getLastRowNum(); i++){
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++){
                cell = row.getCell(j);
                if (numerics.contains(j)) {
                    cell.setCellStyle(setting.getStyles().get("Number"));
                }else{
                    cell.setCellStyle(setting.getStyles().get("Text"));
                }
            }
        }
        for (int i = 0; i<sheet.getRow(0).getLastCellNum(); i++){
            sheet.autoSizeColumn(i);
        }
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    private Double toNumeric(String value){
        try {
            return Double.parseDouble(value);
        }catch (NumberFormatException e){
            return null;
        }
    }
}
