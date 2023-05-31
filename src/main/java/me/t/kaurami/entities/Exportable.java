package me.t.kaurami.entities;

import java.util.HashMap;

/**
 * The interface is a marker of an entity that can be exported to a file.
 * Provides an entity-to-string conversion function for uploading entities to files.
 */
@FunctionalInterface
public interface Exportable {
    HashMap<String, String> toExport();
}
