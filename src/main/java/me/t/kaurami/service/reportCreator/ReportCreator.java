package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.service.bookReader.NotValidWorkbookException;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ReportCreator {

    private LinkedList<LinkedList<String>> data;
    private HashMap<String, Integer> columnNumbers = new HashMap<>();
    private Map<String, Map<String, List<String>>> fields;
    private long creditLimitLevel;
    private Map<String, MarketOwner> ownerHashMap = new HashMap<>();

    public ReportCreator(Map<String, Map<String, List<String>>> fields) {
        this.fields = fields;
    }

    /**
     * Basic data processing
     * @param data Source data for report
     * @param ownerHashMap Map client owner entity
     * @param columnNumbers Matching the required columns in source data
     */
    protected abstract void generateReport(List<LinkedList<String>> data, Map<String, MarketOwner> ownerHashMap, Map<String, Integer> columnNumbers);

    protected abstract List<Exportable> castToExportable(Map<String, MarketOwner> ownerHashMap);

    protected abstract String getReportType();

    /**
     * Cycle processing report data
     * @param sourceData
     * @return
     */
    public List<Exportable> createReport(LinkedList<LinkedList<String>> sourceData) throws NotValidWorkbookException{
        data = sourceData;
        fillColumnNumbers(fields);
        checkingForTheRequiredFields();
        data.removeFirst();
        generateReport(data, ownerHashMap, columnNumbers);
        List<Exportable> exportables = castToExportable(ownerHashMap);
        exportables = additionalProcessing(exportables);
        return exportables;
    }

    public void setLimit(Long limit){
        this.creditLimitLevel = limit;
    }

    /**
     * Additional data processing (Optional)
     * @param exportables
     * @return
     */
    protected List<Exportable> additionalProcessing(List<Exportable> exportables){
        return exportables;
    }

    public long getCreditLimitLevel() {
        return creditLimitLevel;
    }

    private void fillColumnNumbers(Map<String, Map<String, List<String>>> fields){
        Map<String, List<String>> sourceFields = fields.get(getReportType());
        sourceFields.keySet().stream().forEach(f->columnNumbers.put(f, null));
        for (String columnName: data.getFirst()){
            for (Map.Entry entry: sourceFields.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), data.getFirst().indexOf(columnName));
                }
            }
        }
    }

    private void checkingForTheRequiredFields() throws NotValidWorkbookException{
        List<List<String>> lostColumns = columnNumbers.entrySet().stream()
                .filter(e -> e.getValue() == null)
                .map(e->fields.get(getReportType()).get(e.getKey()))
                .collect(Collectors.toList());
        if (!lostColumns.isEmpty()){
            String message = String.format("Книга не содержит обязательные столбцы: %s. " +
                    " Выполнение операции невозможно.", lostColumns.toString()
                    .replace("[[", "\"")
                    .replace("]]", "\"")
                    .replace(", ", "\" или \"")
                    .replace("]\" или \"[", "\", \""));
            throw new NotValidWorkbookException(message);
        }
    }
}
