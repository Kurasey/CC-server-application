package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.entities.MarketOwnerVolumeForming;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("reportCreatorVF")
public class ReportCreatorVolumeFormingClientList implements ReportCreator {
    private LinkedList<LinkedList<String>> data;
    private long creditLimitLevel;
    private HashMap<String, Integer> columnNumbers = new HashMap<>();
    private HashMap<String, String> columnsVolumeForming = new HashMap<String, String>(){{
        put("Кл_Имя","name");
        put("ТО_чистый_период","summaryTurnover");
        put("ТовКред_дн","defrredPayment");
        put("ЛимКред_из_карт","summaryCreditLine");
        put("Кл_ИНН","idividualTaxpayerNumber");
    }};

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
        fillColumnNumbers(columnsVolumeForming);
        HashMap<String, MarketOwnerVolumeForming> ownersHashMap = new HashMap<>();
        String idividualTaxpayerNumber;
        System.out.println(data);
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

    private void fillColumnNumbers(HashMap<String, String> columns){
        for (String columnNames: data.getFirst()){
            if (columns.containsKey(columnNames)){
                columnNumbers.put(columns.get(columnNames), data.getFirst().indexOf(columnNames));
            }
        }
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
    private HashMap<String, String> getColumnsVolumeForming() {
        return columnsVolumeForming;
    }

    @Deprecated
    private LinkedList<LinkedList<String>> getData() {
        return data;
    }
}
