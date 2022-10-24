package me.t.kaurami.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarketOwnerForRelationshipChecker extends MarketOwner implements Exportable{
    private String individualTaxpayerNumber;
    private Set<String> ownerNames;
    private int validMarketsWithCredit;
    private int validMarketsWithoutCredit;
    private int notValidMarkets;
    private int asociatedClient;
    private int associatedWithCredit;
    private int associatedWithoutCredit;
    private int associatedNotValidMarket;
    private String recomendation = "";

    public MarketOwnerForRelationshipChecker(String name, String individualTaxpayerNumber) {
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
            put("asociatedClient", Integer.toString(asociatedClient));
            put("associatedWithCredit", Integer.toString(associatedWithCredit));
            put("associatedWithoutCredit", Integer.toString(associatedWithoutCredit));
            put("associatedNotValidMarket", Integer.toString(associatedNotValidMarket));
            put("associatedNames", ownerNames.toString());
            put("recomendation", recomendation);
        }};
        return values;
    }

    private void createRecomendation() {
        if (ownerNames.size()>1){
            recomendation += "Сократить количество хозяев сети.";
        }

        if (validMarketsWithCredit>2 && associatedWithCredit<validMarketsWithCredit){
            recomendation += "Связать по хозяину все ТТ.";
        }

        if (associatedNotValidMarket == asociatedClient && validMarketsWithCredit < 3){
            recomendation += "Развазять";
        }
    }

/*
    public void addOwnerName(String ownerName) {
        this.ownerNames.add(ownerName);
    } */
/*Yet not use*//*


    public void addValidMarketsWithoutCredit() {
        this.validMarketsWithoutCredit++;
    }

    public void addValidMarketsWithCredit() {
        this.validMarketsWithCredit++;
    }

    public void addNotValidMarkets() {
        this.notValidMarkets++;
    }

    public void addAsociatedClient() {
        this.asociatedClient++;
    }

    public void addAssociatedWithCredit(int associatedWithCredit) {
        this.associatedWithCredit = associatedWithCredit;
    }

    public void addAssociatedWithoutCredit(int associatedWithoutCredit) {
        this.associatedWithoutCredit = associatedWithoutCredit;
    }

    public void addAssociatedNotValidMarket(int associatedNotValidMarket) {
        this.associatedNotValidMarket = associatedNotValidMarket;
    }
*/

    public void updateValues(String status, String defrredPayment, String creditLine, String ownerName){
        if (status.equals("Действующая")){

            if ((parseLong(defrredPayment))>2 && (parseLong(creditLine))>1){
                validMarketsWithCredit++;
                if (checkOwner(ownerName)){
                    asociatedClient++;
                    associatedWithCredit++;
                }
            }else {
                validMarketsWithoutCredit++;
                if (checkOwner(ownerName)){
                    asociatedClient++;
                    associatedWithoutCredit++;
                }
            }


        }else {
            notValidMarkets++;
            if (checkOwner(ownerName)){
                asociatedClient++;
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
