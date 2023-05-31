package me.t.kaurami.service.bookReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for extract data from xls or xlsx a workbook
 */
public interface BookReader {

    /**
     * Add workbook for next steps
     * @param file xls or xlsx workbook
     * @throws IOException
     * @throws NotValidWorkbookException not valid file format
     */
    void loadBook (File file) throws IOException, NotValidWorkbookException;

    /**
     * Select sheet by number for extracting
     * @param selectedSheetNumber Number of sheet at workbook
     */
    void setSelectedSheetNumber(int selectedSheetNumber);

    /**
     * Select sheet by name for extracting
     * @param sheetName Name of sheet at workbook
     */
    void setSelectedSheetNumber(String sheetName);

    /**
     * Set date format for extracted date
     * @param pattern
     */
    void setDateFormat(String pattern);

    /**
     * Get names of all shee from the workbook
     * @return List of names
     */
    List<String> getSheetsNames();

    /**
     * Read selected sheet and extract row to LinkedList(LinkedList) and columns to LinkedList(String)
     * @return LinkedList columns input LinkedList rows
     */
    LinkedList<LinkedList<String>> readSheet();

    /**
     * Get current workbook
     * @return WorkBook
     */
    @Deprecated
    Workbook getWorkbook();
}
