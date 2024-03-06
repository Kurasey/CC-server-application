package me.t.kaurami.web.controller;

import me.t.kaurami.service.reportprocessmanager.ReportProcessManager;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SessionAttributes("settingHolder")
@RequestMapping("/reportmodule/reportparameters")
@Controller
public class SetSettingsController {

    private ReportProcessManager reportProcessManager;

    public SetSettingsController(ReportProcessManager reportProcessManager) {
        this.reportProcessManager = reportProcessManager;
    }

    @GetMapping()
    public String showForm(){
        return "setsettings";
    }

    @PostMapping()
    public String createReport(@RequestParam("sourceFile")MultipartFile multipartFile, @ModelAttribute("settingHolder") SettingHolder applicationSettings) throws Exception{
        reportProcessManager.buildReport();
        return "redirect:/download";
    }
}
