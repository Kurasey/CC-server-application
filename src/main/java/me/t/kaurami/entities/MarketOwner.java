package me.t.kaurami.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Entity representing a customer for reports
 */
public abstract class MarketOwner implements Comparable<MarketOwner> {

    /**
     * The pattern of converting a name into a more visual form.
     */
    private static String namePattern = ".+";


    private final String name;
    private Matcher matcher;

    protected MarketOwner(String name) {
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
    public int compareTo(MarketOwner owner) {
        return this.getName().compareTo(owner.getName());
    }
}
