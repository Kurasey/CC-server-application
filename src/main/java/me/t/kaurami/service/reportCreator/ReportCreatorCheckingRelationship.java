package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.Marowner;
import me.t.kaurami.entities.MarownerForRelationshipChecker;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("reportCreatorCR")
public class ReportCreatorCheckingRelationship implements ReportCreator {

    private SettingHolder settingHolder;
    private LinkedList<LinkedList<String>> data;
    private HashMap<String, Integer> columnNumbers = new HashMap<>();
    private HashMap<String, List<String>> columnsReport = new HashMap<String,  List<String>>(){{
        put("status", Arrays.asList("Статус"));
        put("individualTaxpayerNumber",Arrays.asList("ИНН"));
        put("name",Arrays.asList("Клиент"));
        put("defrredPayment",Arrays.asList("Кол_Дн_ТК"));
        put("creditLine",Arrays.asList("Лимит (Сумма)"));
        put("ownerName", Arrays.asList("Хозяин (сети):"));
    }};

    @Autowired
    public void setSettingHolder(SettingHolder settingHolder) {
        this.settingHolder = settingHolder;
    }

    @Override
    public void setData(LinkedList<LinkedList<String>> data) {
        this.data = data;
    }

    @Override
    public void setLimit(Long limit) {
        /*Not supported*/
    }

    @Override
    public List<Exportable> createReport() {
        fillColumnNumbers(columnsReport);
        HashMap<String, MarownerForRelationshipChecker> ownerHashMap = new HashMap<>();
        data.removeFirst();
        String individualTaxpayerNumber;
        MarownerForRelationshipChecker.setNamePattern(settingHolder.getNamePattern());
        MarownerForRelationshipChecker owner;
        ownerHashMap.put("", new MarownerForRelationshipChecker("Клиенты без ИНН", ""));
        for (LinkedList<String> list: data){
            individualTaxpayerNumber = list.get(columnNumbers.get("individualTaxpayerNumber"));
            if (ownerHashMap.containsKey(individualTaxpayerNumber)){
                owner = ownerHashMap.get(individualTaxpayerNumber);
                owner.updateValues(list.get(columnNumbers.get("status")),
                        list.get(columnNumbers.get("defrredPayment")),
                        list.get(columnNumbers.get("creditLine")),
                        list.get(columnNumbers.get("ownerName")));
            }else {
                owner = new MarownerForRelationshipChecker(
                        list.get(columnNumbers.get("name")),
                        list.get(columnNumbers.get("individualTaxpayerNumber")));
                owner.updateValues(list.get(columnNumbers.get("status")),
                        list.get(columnNumbers.get("defrredPayment")),
                        list.get(columnNumbers.get("creditLine")),
                        list.get(columnNumbers.get("ownerName")));
                ownerHashMap.put(individualTaxpayerNumber, owner);
            }
        }
        List<Exportable> exportables = ownerHashMap.values().stream().sorted(Marowner::compareTo).collect(Collectors.toList());
        return exportables;
    }

    private void fillColumnNumbers(HashMap<String, List<String>> columns) { /* Remove HashMap from args*/
        for (String columnName: data.getFirst()){
            for (Map.Entry entry: columns.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), data.getFirst().indexOf(columnName));
                }
            }
        }
    }
}
