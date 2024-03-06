package me.t.kaurami;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.*;
import java.util.*;

public class TestDataLoader {

    private LinkedList<LinkedList<String>> sourceData = new LinkedList<>();
    private Map<String, Map<String, List<String>>> fields;

    public TestDataLoader(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            LinkedList<String> strings;
            String line;
            while ((line = reader.readLine()) != null){
                strings = new LinkedList<>();
                for (String s : line.split("\t")) {
                    strings.add(s);
                }
                sourceData.add(strings);
            }
            strings = null;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        initFieldsMap();
    }

    private void initFieldsMap() {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("app-context.xml");
        context.refresh();
        fields = context.getBean("fields", Map.class);
        context.close();
    }

    public LinkedList<LinkedList<String>> getSourceData() {
        return new LinkedList<>(sourceData);
    }

    public Map<String, Map<String, List<String>>> getFields() {
        return fields;
    }

}
