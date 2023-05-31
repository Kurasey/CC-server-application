package me.t.kaurami.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Converter for download workbook from Controller
 */
@Component
public class FileConverter implements Converter<MultipartFile, File> {
    @Override
    public File convert(MultipartFile file) {
        try{
            String[] fileName = file.getOriginalFilename().split("\\.");
            File file1 = File.createTempFile("book", "." + fileName[fileName.length-1]);
            file1.deleteOnExit();
            file.transferTo(file1);
            return file1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
