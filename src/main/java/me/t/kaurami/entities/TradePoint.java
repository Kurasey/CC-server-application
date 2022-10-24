package me.t.kaurami.entities;

import java.util.regex.Matcher;

public abstract class TradePoint {
    private String name;
    private String namePattern = "^.+ИП|^.+ООО|^.+ПАО|^.+ЗАО|^.+МУП|^.+ГБУ";
    private Matcher matcher;


}
