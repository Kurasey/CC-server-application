package me.t.kaurami.controller;

import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@SessionAttributes("settingHolder")
@Controller
public class HomeController{

    private SettingHolder settingHolder;

    public HomeController(SettingHolder settingHolder) {
        this.settingHolder = settingHolder;
    }

    @ModelAttribute("settingHolder")
    private SettingHolder getAppSetting(){
        return settingHolder;
    }

    @GetMapping
    public String home(){
        return "home";
    }

    @PostMapping("types")
    public String chooseProfile(@ModelAttribute("settingHolder") SettingHolder settingHolder) throws Exception{
        settingHolder.setActiveSettings();
        return "redirect:/types";
    }

}
