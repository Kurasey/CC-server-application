package me.t.kaurami.controller;

import me.t.kaurami.service.setting.AppSetting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@RequestMapping("/")
@SessionAttributes("applicationSettings")
@Controller
public class HomeController{

    @ModelAttribute("applicationSettings")
    private AppSetting getAppSetting(){
        return new AppSetting();
    }

    @GetMapping
    public String home(){
        return "home";
    }

    @PostMapping("types")
    public String chooseProfile(@ModelAttribute("applicationSettings") AppSetting appSetting){
        return "redirect:/types";
    }

}
