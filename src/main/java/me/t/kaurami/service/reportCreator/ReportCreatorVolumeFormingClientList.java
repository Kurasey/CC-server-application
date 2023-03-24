package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.entities.MarketOwnerForRelationshipChecker;
import me.t.kaurami.entities.MarketOwnerVolumeForming;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("VOLUME_FORMING_reportCreator")
public class ReportCreatorVolumeFormingClientList implements ReportCreator {

    private SettingHolder settingHolder;
    private LinkedList<LinkedList<String>> data;
    private long creditLimitLevel;
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
        this.creditLimitLevel = limit;
    }

    @Override
    public List<Exportable> createReport(){
        fillColumnNumbers(fields);
        HashMap<String, MarketOwnerVolumeForming> ownersHashMap = new HashMap<>();
        String idividualTaxpayerNumber;
        MarketOwnerForRelationshipChecker.setNamePattern(settingHolder.getNamePattern());
        data.removeFirst();
        for (LinkedList<String> linkedList: data){
            idividualTaxpayerNumber = linkedList.get(columnNumbers.get("idividualTaxpayerNumber"));
            if (ownersHashMap.containsKey(idividualTaxpayerNumber)){
                ownersHashMap.get(idividualTaxpayerNumber).addMarket(
                        parseInt(linkedList.get(columnNumbers.get("summaryCreditLine"))),
                        parseInt(linkedList.get(columnNumbers.get("defrredPayment"))),
                        parseInt(linkedList.get(columnNumbers.get("summaryTurnover"))));
            }else {
                ownersHashMap.put(idividualTaxpayerNumber, new MarketOwnerVolumeForming(
                        linkedList.get(columnNumbers.get("name")),
                        idividualTaxpayerNumber,
                        parseInt(linkedList.get(columnNumbers.get("summaryCreditLine"))),
                        parseInt(linkedList.get(columnNumbers.get("defrredPayment"))),
                        parseInt(linkedList.get(columnNumbers.get("summaryTurnover")))));
            }
        }
        List<Exportable> volumeFormingClientList = ownersHashMap.values().stream()
                .filter(e->e.getSummaryCreditLine() >= creditLimitLevel)
                .sorted(MarketOwner::compareTo)
                .collect(Collectors.toList());

        return volumeFormingClientList;
    }

    private void fillColumnNumbers(Map<String, Map<String, List<String>>> fields){
        System.out.println(fields);
        Map<String, List<String>> sourceFields = fields.get("VOLUME_FORMING");
        for (String columnName: data.getFirst()){
            for (Map.Entry entry: sourceFields.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), data.getFirst().indexOf(columnName));
                }
            }
        }
        System.out.println(columnNumbers);
    }

    private int parseInt(String value){
        try {
            return (int)Double.parseDouble(value);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    @Deprecated
    private HashMap<String, Integer> getColumnNumbers() {
        return columnNumbers;
    }


    @Deprecated
    public LinkedList<LinkedList<String>> getData() {
        return data;
    }
}
