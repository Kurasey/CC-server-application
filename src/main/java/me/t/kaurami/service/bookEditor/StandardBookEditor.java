package me.t.kaurami.service.bookEditor;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.setting.ReportFormatHolder;
import me.t.kaurami.service.setting.SettingHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.Resource;
import java.util.*;

@Service
@SessionScope
public class StandardBookEditor implements BookEditor {
    private static Logger logger = LoggerFactory.getLogger(BookEditor.class);
    private Workbook workbook;
    private Sheet sheet;
    private Row row;
    private Cell cell;
    private ReportFormatHolder setting;
    private Map<String, ReportFormatHolder> formatHolderMap;

    @Override
    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }


    @Resource(name = "formatHolders")
    public void setFormatHolderMap(Map<String, ReportFormatHolder> formatHolderMap) {
        this.formatHolderMap = formatHolderMap;
    }

    @Deprecated
    public void setSetting(ReportFormatHolder setting) {
        this.setting = setting;
    }

    @Override
    public Workbook createReportFile(List<Exportable> report/*refactor to interface*/){
        Workbook editableBook;
        logger.info("Creating the final report workbook...");
        if (workbook==null){
            editableBook = new XSSFWorkbook();
        }else {
            editableBook = workbook;
            workbook = null;
        }
        sheet = editableBook.createSheet(setting.getSheetName());
        int rowNum = 1;
        HashMap<String, String> values;
        for (Exportable exportable: report){
            values = exportable.toExport();
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
        logger.info("Report file successfully formed");
        applyStyles(editableBook);
        return editableBook;
    }

    @Override
    public boolean setReportType(SettingHolder.ReportType reportType) {
        setting = formatHolderMap.get(reportType.toString());
        return true;
    }

    private void applyStyles(Workbook editableBook){
        logger.info("Formatting report file...");
        setting.createStyles(editableBook);
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
        logger.info("Report file successfully formatted");
    }

    private Double toNumeric(String value){
        try {
            return Double.parseDouble(value);
        }catch (NumberFormatException e){
            return null;
        }
    }
}
