package me.t.kaurami.web.controller;

import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SessionAttributes("settingHolder")
@RequestMapping("/download")
@Controller
public class FileUploadController {

    @Autowired
    SettingHolder settingHolder;

    @GetMapping(params = "reportFile")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(SessionStatus sessionStatus) throws Exception {
        String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_" + settingHolder.getType().getName().replace(" ", "_") + ".xlsx";
        File reportFile = new File(fileName);
        try (FileOutputStream outputStream = new FileOutputStream(reportFile)) {
            settingHolder.getTargetFile().write(outputStream);
        }
        Resource file = new UrlResource(reportFile.toURI());
        sessionStatus.setComplete();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(file);
    }


    @GetMapping()
    public String getForm(SettingHolder settingHolder){
        return "download";
    }

}
