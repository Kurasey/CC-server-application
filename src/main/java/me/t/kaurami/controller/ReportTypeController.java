package me.t.kaurami.controller;

import me.t.kaurami.service.setting.AppSetting;
import me.t.kaurami.service.setting.PreReportSetting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SessionAttributes("applicationSettings")
@RequestMapping()
@Controller
public class ReportTypeController {

    @GetMapping("/types")
    public String getForm(){
        return "types";
    }


    @PostMapping("nextStep")
    public /*@ResponseBody*/ String handleFileUpload(@ModelAttribute("applicationSettings") AppSetting appSetting) throws Exception{
        return "redirect:/reportSetting";
    }

    @ModelAttribute("reportTypes")
    private List<PreReportSetting.ReportType> reportTypes(){
        return Arrays.asList(PreReportSetting.ReportType.values());
    }


}
