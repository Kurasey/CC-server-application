package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.entities.MarketOwnerVolumeForming;
import me.t.kaurami.service.setting.SettingHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;
import java.util.stream.Collectors;

@Service("VOLUME_FORMING_reportCreator")
@RequestScope
public class ReportCreatorVolumeFormingClientList extends ReportCreator {

    private SettingHolder settingHolder;

    public ReportCreatorVolumeFormingClientList(@Value(value = ("#{fields}")) Map<String, Map<String, List<String>>> fields, SettingHolder settingHolder) {
        super(fields);
        this.settingHolder = settingHolder;
    }

    @Override
    protected List<Exportable> castToExportable(Map<String, MarketOwner> ownerHashMap) {
        return ownerHashMap.values().stream().map(o -> (MarketOwnerVolumeForming) o).collect(Collectors.toList());
    }

    @Override
    protected String getReportType() {
        return settingHolder.getType().toString();
    }

    @Override
    protected void generateReport(List<LinkedList<String>> data, Map<String, MarketOwner> ownerHashMap, Map<String, Integer> columnNumbers) {
        String individualTaxpayerNumber;
        for (LinkedList<String> linkedList: data){
            individualTaxpayerNumber = linkedList.get(columnNumbers.get("idividualTaxpayerNumber"));
            if (ownerHashMap.containsKey(individualTaxpayerNumber)){
                ((MarketOwnerVolumeForming)ownerHashMap.get(individualTaxpayerNumber)).addMarket(
                        parseInt(linkedList.get(columnNumbers.get("summaryCreditLine"))),
                        parseInt(linkedList.get(columnNumbers.get("defrredPayment"))),
                        parseInt(linkedList.get(columnNumbers.get("summaryTurnover"))));
            }else {
                ownerHashMap.put(individualTaxpayerNumber, new MarketOwnerVolumeForming(
                        linkedList.get(columnNumbers.get("name")),
                        individualTaxpayerNumber,
                        parseInt(linkedList.get(columnNumbers.get("summaryCreditLine"))),
                        parseInt(linkedList.get(columnNumbers.get("defrredPayment"))),
                        parseInt(linkedList.get(columnNumbers.get("summaryTurnover")))));
            }
        }
    }

    @Override
    protected List<Exportable> additionalProcessing(List<Exportable> exportables) {
        long limit = getCreditLimitLevel();
        return exportables.stream().filter(o -> ((MarketOwnerVolumeForming) o).getSummaryCreditLine() > limit).collect(Collectors.toList());
    }

    private int parseInt(String value){
        try {
            return (int)Double.parseDouble(value);
        }catch (NumberFormatException e){
            return 0;
        }
    }
}
