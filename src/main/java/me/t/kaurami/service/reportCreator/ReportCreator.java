package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class ReportCreator {

    private LinkedList<LinkedList<String>> data;
    private HashMap<String, Integer> columnNumbers = new HashMap<>();
    private Map<String, Map<String, List<String>>> fields;
    private long creditLimitLevel;
    private Map<String, MarketOwner> ownerHashMap = new HashMap<>();

    public ReportCreator(Map<String, Map<String, List<String>>> fields) {
        this.fields = fields;
    }

    protected abstract void generateReport(List<LinkedList<String>> data, Map<String, MarketOwner> ownerHashMap, Map<String, Integer> columnNumbers);

    protected abstract List<Exportable> castToExportable(Map<String, MarketOwner> ownerHashMap);

    protected abstract String getReportType();

    public final List<Exportable> createReport(LinkedList<LinkedList<String>> sourceData) {
        data = sourceData;
        fillColumnNumbers(fields);
        data.removeFirst();
        generateReport(data, ownerHashMap, columnNumbers);
        List<Exportable> exportables = castToExportable(ownerHashMap);
        exportables = additionalProcessing(exportables);
        return exportables;
    }

    public void setLimit(Long limit){
        this.creditLimitLevel = limit;
    }

    protected List<Exportable> additionalProcessing(List<Exportable> exportables){
        return exportables;
    }

    public long getCreditLimitLevel() {
        return creditLimitLevel;
    }

    private void fillColumnNumbers(Map<String, Map<String, List<String>>> fields){
        Map<String, List<String>> sourceFields = fields.get(getReportType());
        System.err.println(getReportType());
        System.err.println(sourceFields);
        for (String columnName: data.getFirst()){
            for (Map.Entry entry: sourceFields.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), data.getFirst().indexOf(columnName));
                }
            }
        }
    }

}
