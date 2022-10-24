package me.t.kaurami.service.bookWriter;

import org.apache.poi.ss.usermodel.Workbook;

public interface BookWriter {
    public void writeBook(String path, Workbook workbook);
}
