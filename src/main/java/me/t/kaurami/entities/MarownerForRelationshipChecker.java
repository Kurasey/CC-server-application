package me.t.kaurami.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MarownerForRelationshipChecker extends Marowner implements Exportable{
    private String individualTaxpayerNumber;
    private Set<String> ownerNames;
    private int validMarketsWithCredit;
    private int validMarketsWithoutCredit;
    private int notValidMarkets;
    private int associatedClient;
    private int associatedWithCredit;
    private int associatedWithoutCredit;
    private int associatedNotValidMarket;
    private String recommendation = "";

    public MarownerForRelationshipChecker(String name, String individualTaxpayerNumber) {
        super(name);
        this.individualTaxpayerNumber = individualTaxpayerNumber;
        ownerNames = new HashSet<>();
    }

    @Override
    public HashMap<String, String> toExport() {
        createRecomendation();
        HashMap<String, String> values = new HashMap<String, String>(){{
            put("name",getName());
            put("individualTaxpayerNumber", individualTaxpayerNumber);
            put("validMarketsWithCredit", Integer.toString(validMarketsWithCredit));
            put("validMarketsWithoutCredit", Integer.toString(validMarketsWithoutCredit));
            put("notValidMarkets", Integer.toString(notValidMarkets));
            put("associatedClient", Integer.toString(associatedClient));
            put("associatedWithCredit", Integer.toString(associatedWithCredit));
            put("associatedWithoutCredit", Integer.toString(associatedWithoutCredit));
            put("associatedNotValidMarket", Integer.toString(associatedNotValidMarket));
            put("associatedNames", ownerNames.toString());
            put("recommendation", recommendation);
        }};
        return values;
    }

    private void createRecomendation() {
        if (ownerNames.size()>1){
            recommendation += "Сократить количество хозяев сети.";
        }
        if (validMarketsWithCredit>2 && associatedWithCredit<validMarketsWithCredit){
            recommendation += "Связать по хозяину все ТТ.";
        }
        if (associatedNotValidMarket == associatedClient && validMarketsWithCredit < 3 && ownerNames.size()>0){
            recommendation += "Развазять";
        }
    }

    public void updateValues(String status, String defrredPayment, String creditLine, String ownerName){
        if (status.equals("Действующая")){
            if ((parseLong(defrredPayment))>2 && (parseLong(creditLine))>1){
                validMarketsWithCredit++;
                if (checkOwner(ownerName)){
                    associatedClient++;
                    associatedWithCredit++;
                }
            }else {
                validMarketsWithoutCredit++;
                if (checkOwner(ownerName)){
                    associatedClient++;
                    associatedWithoutCredit++;
                }
            }
        }else {
            notValidMarkets++;
            if (checkOwner(ownerName)){
                associatedClient++;
                associatedNotValidMarket++;
            }
        }
    }

    private boolean checkOwner(String ownerName) {
        if(ownerName != null && ownerName.length()>1){
            ownerNames.add(ownerName);
            return true;
        }
        return false;
    }

    private long parseLong(String value){
        try {
            Float f = Float.valueOf(value);
            return f.longValue();
        }catch (Exception e){
            return 2;
        }
    }
}
