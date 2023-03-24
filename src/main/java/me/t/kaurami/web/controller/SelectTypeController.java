package me.t.kaurami.web.controller;

import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@SessionAttributes("settingHolder")
@RequestMapping("/reportmodule/selecttype")
@Controller
public class SelectTypeController {

    private SettingHolder settingHolder;

    public SelectTypeController(SettingHolder settingHolder) {
        this.settingHolder = settingHolder;
    }

    @ModelAttribute("settingHolder")
    private SettingHolder getSettingHolder(){
        return settingHolder;
    }

    @GetMapping()
    public String getForm(){
        return "selecttype";
    }


    @PostMapping()
    public /*@ResponseBody*/ String handleFileUpload(@ModelAttribute("settingHolder") SettingHolder appSetting) throws Exception{
        return "redirect:/reportmodule/reportparameters";
    }

    @ModelAttribute("reportTypes")
    private List<SettingHolder.ReportType> reportTypes(){
        return Arrays.asList(SettingHolder.ReportType.values());
    }

}
