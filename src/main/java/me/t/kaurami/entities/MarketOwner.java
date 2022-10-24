package me.t.kaurami.entities;

import me.t.kaurami.service.converter.NameConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MarketOwner implements Comparable<MarketOwner> {
    private final String name;
    private static NameConverter nameChecker;
    private String namePattern = "^.+ИП|^.+ООО|^.+ПАО|^.+ЗАО|^.+МУП|^.+ГБУ";
    private Matcher matcher;

    protected MarketOwner(String name) {
        this.name = correctName(name);
    }

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public String getName() {
        return name;
    }

    private String correctName(String name){
        matcher = Pattern.compile(getNamePattern()).matcher(name);
        if (matcher.find()){
            return matcher.group();
        }
        return name;
    }

    @Override
    public int compareTo(MarketOwner o) {
        return this.getName().compareTo(o.getName());
    }
}
