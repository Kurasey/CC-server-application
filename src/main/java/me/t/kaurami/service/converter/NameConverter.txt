package me.t.kaurami.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(value = "nameConverter")
public class NameConverter {
    private String namePattern;
    private Matcher matcher;

    @Autowired
    @Qualifier(value = "nameTrimmingPattern")
    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public String convert(String name){
        matcher = Pattern.compile(namePattern).matcher(name);
        if (matcher.find()){
            return matcher.group();
        }
        return name;
    }
}
