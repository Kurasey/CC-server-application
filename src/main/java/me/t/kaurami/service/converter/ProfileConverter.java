package me.t.kaurami.service.converter;

import me.t.kaurami.service.setting.Profile;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProfileConverter implements Converter<String, Profile>, ApplicationContextAware {
    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public Profile convert(String source) {
        return (Profile) context.getBean("profiles", HashMap.class).get(source);
    }
}
