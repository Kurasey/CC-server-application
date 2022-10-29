package me.t.kaurami.controller;

import me.t.kaurami.service.builder.ReportBuilder;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SessionAttributes("settingHolder")
@RequestMapping()
@Controller
public class ReportSettingController {

    private ReportBuilder reportBuilder;

    public ReportSettingController(ReportBuilder reportBuilder) {
        this.reportBuilder = reportBuilder;
    }

    @GetMapping("reportSetting")
    public String showForm(){
        return "reportSetting";
    }

    @PostMapping("reportSetting")
    public String createReport(@RequestParam("sourceFile")MultipartFile multipartFile, @ModelAttribute("settingHolder") SettingHolder applicationSettings) throws Exception{
        reportBuilder.createReport();
        return "redirect:/download";
    }
}
