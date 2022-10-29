package me.t.kaurami.controller;

import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@SessionAttributes("settingHolder")
@RequestMapping()
@Controller
public class ReportTypeController {

    @GetMapping("/types")
    public String getForm(){
        return "types";
    }


    @PostMapping("typess")
    public /*@ResponseBody*/ String handleFileUpload(@ModelAttribute("settingHolder") SettingHolder appSetting) throws Exception{
        return "redirect:/reportSetting";
    }

    @ModelAttribute("reportTypes")
    private List<SettingHolder.ReportType> reportTypes(){
        return Arrays.asList(SettingHolder.ReportType.values());
    }


}
