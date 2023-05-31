package me.t.kaurami.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a customer for report of relationship of some customers
 */
public class MarketOwnerForRelationshipChecker extends MarketOwner implements Exportable{

    /**
     * Individual taxpayer number
     */
    private String individualTaxpayerNumber;
    /**
     * All alias of customer trade point in Access database
     */
    private Set<String> ownerNames;
    /**
     * Active trade point of customer shipped on the basis of deferred payment
     */
    private int validMarketsWithCredit;
    /**
     * Active trade point of customer shipped on prepayment
     */
    private int validMarketsWithoutCredit;
    /**
     * Inactive trade point of customer
     */
    private int notValidMarkets;
    /**
     * Count associated trade point of the customer
     */
    private int associatedClient;
    /**
     * Count associated trade point of the customer shipped on the basis of deferred payment
     */
    private int associatedWithCredit;
    /**
     * Count associated trade point of the customer shipped on prepayment
     */
    private int associatedWithoutCredit;
    /**
     * Count associated inactive trade point of the customer
     */
    private int associatedNotValidMarket;
    /**
     * End recommendation for credit controller
     */
    private String recommendation = "";

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
            put("associatedClient", Integer.toString(associatedClient));
            put("associatedWithCredit", Integer.toString(associatedWithCredit));
            put("associatedWithoutCredit", Integer.toString(associatedWithoutCredit));
            put("associatedNotValidMarket", Integer.toString(associatedNotValidMarket));
            put("associatedNames", ownerNames.toString());
            put("recommendation", recommendation);
        }};
        return values;
    }

    /**
     * Create recommendation about next step for credit controller
     */
    private void createRecomendation() { //rework to RecommendationService
        if (ownerNames.size()>1){
            recommendation += "Сократить количество хозяев сети. ";
        }
        if (validMarketsWithCredit>2 && associatedWithCredit<validMarketsWithCredit && ownerNames.size()>0){
            recommendation += "Связать по хозяину все ТТ. ";
        }if (validMarketsWithCredit>2 && ownerNames.size()==0){
            recommendation += "Связать. ";
        }
        if (validMarketsWithCredit < 2 && associatedClient > 2 && ownerNames.size()>0){
            recommendation += "Возможно следует развазять. ";
        }
        if (validMarketsWithCredit < 2 && associatedClient < 2 && ownerNames.size()>0){
            recommendation += "Развазять. ";
        }
    }

    /**
     *  Update the current customer entity when a new point of sale is detected
     * @param status trade point status - active/inactive
     * @param defrredPayment - Agreed deferred payment for shipments to customers
     * @param creditLine - Current credit limit
     * @param ownerName - Name of customer
     */
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

    /**
     * Сhecking for an empty value
     * @param ownerName Name of customer
     * @return true if client name is valid
     */
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
