package me.t.kaurami.service.setting;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownSettingException extends Exception {

    public UnknownSettingException(String message){
        super(message);
    }

}
