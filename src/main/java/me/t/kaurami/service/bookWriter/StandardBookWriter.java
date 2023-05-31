package me.t.kaurami.service.bookWriter;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Basic implementation of BookWriter. Write book to path as xlsx book.
 */
@Service
public class StandardBookWriter implements BookWriter {
    private static Logger logger = LoggerFactory.getLogger(BookWriter.class);
    /**
     * Write book to path as xlsx book.
     * @param path path where save workbook
     * @param workbook
     */
    public synchronized void writeBook(String path, Workbook workbook){
        logger.info(String.format("Attempt to write to a file '%s'", path));
        try(OutputStream os = new FileOutputStream(path + ".xlsx")){
            workbook.write(os);
        }catch (FileNotFoundException e){
            logger.error(String.format("Failed recording attempt: %s", e.toString()));
        }catch (IOException e){
            logger.error(String.format("Failed recording attempt: %s", e.toString()));
        }
        logger.info("The report successfully written to the file");
    }




}
