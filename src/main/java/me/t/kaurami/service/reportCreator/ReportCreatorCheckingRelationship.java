package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.entities.MarketOwnerForRelationshipChecker;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("CHECKING_RELATIONSHIP_reportCreator")
public class ReportCreatorCheckingRelationship extends ReportCreator {

    SettingHolder settingHolder;


    public ReportCreatorCheckingRelationship(@Value(value = ("#{fields}")) Map<String, Map<String, List<String>>> fields, SettingHolder settingHolder) {
        super(fields);
        this.settingHolder = settingHolder;
    }

    @Override
    protected List<Exportable> castToExportable(Map<String, MarketOwner> ownerHashMap) {
        return ownerHashMap.values().stream().map(o -> (MarketOwnerForRelationshipChecker)o).collect(Collectors.toList());
    }

    @Override
    protected String getReportType() {
        return settingHolder.getType().toString();
    }

    @Override
    protected void generateReport(List<LinkedList<String>> data, Map<String, MarketOwner> ownerHashMap, Map<String, Integer> columnNumbers) {
        String individualTaxpayerNumber;
        MarketOwnerForRelationshipChecker.setNamePattern(settingHolder.getNamePattern());
        MarketOwnerForRelationshipChecker owner;
        ownerHashMap.put("", new MarketOwnerForRelationshipChecker("Клиенты без ИНН", ""));
        for (LinkedList<String> list: data){
            individualTaxpayerNumber = list.get(columnNumbers.get("individualTaxpayerNumber"));
            if (ownerHashMap.containsKey(individualTaxpayerNumber)){
                owner =((MarketOwnerForRelationshipChecker)ownerHashMap.get(individualTaxpayerNumber));
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
    }
}
