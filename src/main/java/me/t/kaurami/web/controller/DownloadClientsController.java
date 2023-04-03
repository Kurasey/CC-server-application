package me.t.kaurami.web.controller;

import me.t.kaurami.data.DataUploader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RequestMapping("/downloadclients")
@Controller
public class DownloadClientsController {

    private DataUploader dataUploader;

    public DownloadClientsController(DataUploader dataUploader) {
        this.dataUploader = dataUploader;
    }

    @GetMapping()
    public String showForm(){
        return "uploadclientlist";
    }

    @PostMapping()
    public String downloadClients(@RequestParam("sourceFile") File file) throws Exception {
        dataUploader.uploadData(file);
        return "redirect:/";
    }


}
