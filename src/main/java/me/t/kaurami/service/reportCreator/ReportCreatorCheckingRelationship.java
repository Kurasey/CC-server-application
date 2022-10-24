package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.entities.MarketOwner;
import me.t.kaurami.entities.MarketOwnerForRelationshipChecker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("reportCreatorCR")
public class ReportCreatorCheckingRelationship implements ReportCreator {
    LinkedList<LinkedList<String>> data;
    private HashMap<String, Integer> columnNumbers = new HashMap<>();
    private HashMap<String, List<String>> columnsReport = new HashMap<String,  List<String>>(){{
        put("status", Arrays.asList("Статус"));
        put("individualTaxpayerNumber",Arrays.asList("ИНН"));
        put("name",Arrays.asList("Клиент"));
        put("defrredPayment",Arrays.asList("Кол_Дн_ТК"));
        put("creditLine",Arrays.asList("Лимит (Сумма)"));
        put("ownerName", Arrays.asList("Хозяин (сети):"));
    }};

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
        HashMap<String, MarketOwnerForRelationshipChecker> ownerHashMap = new HashMap<>();
        data.removeFirst();
        String individualTaxpayerNumber;
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

    private void fillColumnNumbers(HashMap<String, List<String>> columns) { /* Remove HashMap from args*/
        for (String columnName: data.getFirst()){
            for (Map.Entry entry: columns.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), data.getFirst().indexOf(columnName));
                }
            }
        }
    }

  /*  private void updateOwnerStatus(List<String> list, MarketOwnerForRelationshipChecker owner){

        if (list.get(columnNumbers.get("status")).equals("Действующая")){
            if ((parseLong(list.get(columnNumbers.get("defrredPayment"))))>2 && (parseLong(list.get(columnNumbers.get("creditLine"))))>1){
                owner.addValidMarketsWithCredit();
            }else {
                owner.addValidMarketsWithoutCredit();
            }
        }else {
            owner.addNotValidMarkets();
        }
        if (list.get(columnNumbers.get("ownerName"))!=null && list.get(columnNumbers.get("ownerName")).length()>1){
            owner.addAsociatedClient();
        }
    }


    private long parseLong(String value){
        try {
            Float f = Float.valueOf(value);
            return f.longValue();
        }catch (Exception e){
            return 2;
        }
    }*/
}
