package me.t.kaurami.web.controller;

import me.t.kaurami.service.databaseplaseholder.ExcelDataBasePlaceholder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RequestMapping("/downloadclients")
@Controller
public class DownloadClientsController implements AvailableFromHomepage{

    private ExcelDataBasePlaceholder dataUploader;

    public DownloadClientsController(ExcelDataBasePlaceholder dataUploader) {
        this.dataUploader = dataUploader;
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public String showForm(){
        return "uploadclientlist";
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public String downloadClients(@RequestParam("sourceFile") File file) throws Exception {
        dataUploader.uploadData(file);
        return "redirect:/";
    }

    @Override
    public String getName() {
        return "Импорт списка клиентов (Excel из Access). Только авторизованные пользователи.";
    }

    @Override
    public String getReference() {
        return this.getClass().getAnnotation(RequestMapping.class).value()[0];
    }

}
