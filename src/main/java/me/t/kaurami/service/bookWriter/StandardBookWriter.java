package me.t.kaurami.service.bookWriter;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class StandardBookWriter implements BookWriter {

    public void writeBook(String path, Workbook workbook){
        try(OutputStream os = new FileOutputStream(path + ".xlsx")){
            workbook.write(os);
        }catch (FileNotFoundException e){
            System.err.println(e);
        }catch (IOException e){
            System.err.println(e);
        }
    }




}
