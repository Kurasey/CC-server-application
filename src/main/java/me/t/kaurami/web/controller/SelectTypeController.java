package me.t.kaurami.web.controller;

import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@SessionAttributes("settingHolder")
@RequestMapping("/reportmodule/selecttype")
@Controller
public class SelectTypeController implements AvailableFromHomepage{

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
    public String postMethodHandler(@ModelAttribute("settingHolder") SettingHolder appSetting) throws Exception{
        return "redirect:/reportmodule/reportparameters";
    }

    @ModelAttribute("reportTypes")
    private List<SettingHolder.ReportType> reportTypes(){
        return Arrays.asList(SettingHolder.ReportType.values());
    }

    @Override
    public String getName() {
        return "Формирование отчета";
    }

    @Override
    public String getReference() {
        return this.getClass().getAnnotation(RequestMapping.class).value()[0];
    }
}
