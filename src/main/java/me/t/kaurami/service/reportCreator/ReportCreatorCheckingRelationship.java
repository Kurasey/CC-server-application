package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.entities.MarketOwnerForRelationshipChecker;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("CHECKING_RELATIONSHIP_reportCreator")
public class ReportCreatorCheckingRelationship implements ReportCreator {

    private SettingHolder settingHolder;
    private LinkedList<LinkedList<String>> data;
    private HashMap<String, Integer> columnNumbers = new HashMap<>();
    private Map<String, Map<String, List<String>>> fields;

    @Autowired
    public void setSettingHolder(SettingHolder settingHolder) {
        this.settingHolder = settingHolder;
    }

    @Resource(name = "fields")
    public void setFields(Map<String, Map<String, List<String>>> fields) {
        this.fields = fields;
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
        fillColumnNumbers(fields);
        HashMap<String, MarketOwnerForRelationshipChecker> ownerHashMap = new HashMap<>();
        data.removeFirst();
        String individualTaxpayerNumber;
        MarketOwnerForRelationshipChecker.setNamePattern(settingHolder.getNamePattern());
        MarketOwnerForRelationshipChecker owner;
        ownerHashMap.put("", new MarketOwnerForRelationshipChecker("Клиенты без ИНН", ""));
        for (LinkedList<String> list: data){
            individualTaxpayerNumber = list.get(columnNumbers.get("individualTaxpayerNumber"));
            if (ownerHashMap.containsKey(individualTaxpayerNumber)){
                owner = ownerHashMap.get(individualTaxpayerNumber);
                owner.updateValues(list.get(columnNumbers.get("status")),
                        list.get(columnNumbers.get("defrredPayment")),
                        list.get(columnNumbers.get("creditLine")),
                        list.get(columnNumbers.get("ownerName")));
            }else {
                owner = new MarketOwnerForRelationshipChecker(
                        list.get(columnNumbers.get("name")),
                        list.get(columnNumbers.get("individualTaxpayerNumber")));
                owner.updateValues(list.get(columnNumbers.get("status")),
                        list.get(columnNumbers.get("defrredPayment")),
                        list.get(columnNumbers.get("creditLine")),
                        list.get(columnNumbers.get("ownerName")));
                ownerHashMap.put(individualTaxpayerNumber, owner);
            }
        }
        List<Exportable> exportables = ownerHashMap.values().stream().sorted(MarketOwner::compareTo).collect(Collectors.toList());
        return exportables;
    }

    private void fillColumnNumbers(Map<String, Map<String, List<String>>> fields){
        Map<String, List<String>> sourceFields = fields.get("CHECKING_RELATIONSHIP");
        for (String columnName: data.getFirst()){
            for (Map.Entry entry: sourceFields.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), data.getFirst().indexOf(columnName));
                }
            }
        }
        System.out.println(sourceFields);
        System.out.println(data.getFirst());
        System.out.println(columnNumbers);
    }
}
