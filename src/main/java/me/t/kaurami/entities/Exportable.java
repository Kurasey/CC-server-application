package me.t.kaurami.entities;

import java.util.HashMap;

@FunctionalInterface
public interface Exportable {
    HashMap<String, String> toExport();
}
