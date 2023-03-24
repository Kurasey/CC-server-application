package me.t.kaurami.web.controller;

import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes("settingHolder")
@RequestMapping("/download")
@Controller
public class DownloadController {

    @GetMapping()
    public String getForm(SettingHolder settingHolder){
        return "download";
    }

    @PostMapping
    public String endAction(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
