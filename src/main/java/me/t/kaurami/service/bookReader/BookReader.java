package me.t.kaurami.service.bookReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface BookReader {
    void loadBook (File file) throws IOException, InvalidFormatException;

    void setSelectedSheetNumber(int selectedSheetNumber);

    void setSelectedSheetNumber(String sheetName);

    void setDateFormat(String pattern);

    List<String> getSheetsNames();

    LinkedList<LinkedList<String>> readSheet();

    @Deprecated
    Workbook getWorkbook();
}
