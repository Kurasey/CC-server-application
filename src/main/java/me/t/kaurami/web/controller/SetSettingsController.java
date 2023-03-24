package me.t.kaurami.web.controller;

import me.t.kaurami.service.builder.ReportBuilder;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SessionAttributes("settingHolder")
@RequestMapping("/reportmodule/reportparameters")
@Controller
public class SetSettingsController {

    private ReportBuilder reportBuilder;

    public SetSettingsController(ReportBuilder reportBuilder) {
        this.reportBuilder = reportBuilder;
    }

    @GetMapping()
    public String showForm(){
        return "setsettings";
    }

    @PostMapping()
    public String createReport(@RequestParam("sourceFile")MultipartFile multipartFile, @ModelAttribute("settingHolder") SettingHolder applicationSettings) throws Exception{
        reportBuilder.buildReport();
        return "redirect:/download";
    }
}
