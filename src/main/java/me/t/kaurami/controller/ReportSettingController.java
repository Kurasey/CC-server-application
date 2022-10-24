package me.t.kaurami.controller;

import me.t.kaurami.service.builder.ReportBuilder;
import me.t.kaurami.service.setting.AppSetting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SessionAttributes("applicationSettings")
@RequestMapping()
@Controller
public class ReportSettingController {

    @GetMapping("reportSetting")
    public String showForm(){
        return "selectFile";
    }

    @PostMapping("createReport")
    public String createReport(@RequestParam("file")MultipartFile multipartFile, @ModelAttribute("applicationSettings") AppSetting applicationSettings) throws Exception{
        new ReportBuilder(applicationSettings).createReport();
        return "redirect:/download";
    }
}
