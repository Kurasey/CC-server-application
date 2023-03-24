package me.t.kaurami.service.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

public class UpdatingDbEvent extends ApplicationEvent {

    private String message;

    public UpdatingDbEvent(Object source) {
        super(source);
        this.message = "The db was updated at " + new Date().toString();
    }

}
