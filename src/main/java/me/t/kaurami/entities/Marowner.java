package me.t.kaurami.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Marowner implements Comparable<Marowner> {

    private static String namePattern = ".+";

    private final String name;
    private Matcher matcher;

    protected Marowner(String name) {
        this.name = correctName(name);
    }

    public String getName() {
        return name;
    }

    public static void setNamePattern(String pattern) {
        namePattern = pattern;
    }


    private String correctName(String name){
        matcher = Pattern.compile(namePattern).matcher(name);
        if (matcher.find()){
            return matcher.group();
        }
        return name;
    }

    @Override
    public int compareTo(Marowner owner) {
        return this.getName().compareTo(owner.getName());
    }
}
