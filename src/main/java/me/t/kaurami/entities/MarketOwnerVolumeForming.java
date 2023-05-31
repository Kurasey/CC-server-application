package me.t.kaurami.entities;

import java.util.HashMap;

/**
 * Entity representing a customer for create of volume forming client list
 */
public class MarketOwnerVolumeForming extends MarketOwner implements Exportable {
    /**
     * Individual taxpayer number
     */
    private String idividualTaxpayerNumber;
    /**
     * Count of trade point of the customer
     */
    private int marketCount;
    /**
     * Sum of credit lines of all trade point of the customer
     */
    private int summaryCreditLine;
    /**
     * Agreed deferred payment of the client
     */
    private int deferredPayment;
    /**
     * Sum of turnover of all trade point of the customer
     */
    private int summaryTurnover;

    public MarketOwnerVolumeForming(String name, String idividualTaxpayerNumber, int summaryCreditLine, int deferredPayment, int summaryTurnover) {
        super(name);
        if (isCorrectITN(idividualTaxpayerNumber)){
            this.idividualTaxpayerNumber = idividualTaxpayerNumber;
        }else {
            this.idividualTaxpayerNumber = "406";
        }
        this.marketCount = 1;
        this.summaryCreditLine = summaryCreditLine;
        this.deferredPayment = deferredPayment;
        this.summaryTurnover = summaryTurnover;
    }

    /**
     * Add information about trade point to customer entity
     * @param creditLine Credit line of trade point
     * @param deferredPayment deferred payment of trade point
     * @param turnover Turnover of trade point
     */
    public void addMarket(int creditLine, int deferredPayment, int turnover){
        this.marketCount += 1;
        this.summaryCreditLine += creditLine;
        this.deferredPayment = deferredPayment > this.deferredPayment ? deferredPayment : this.deferredPayment;
        this.summaryTurnover += turnover;
    }

    public String getIdividualTaxpayerNumber() {
        return idividualTaxpayerNumber;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public int getSummaryCreditLine() {
        return summaryCreditLine;
    }

    public int getDeferredPayment() {
        return deferredPayment;
    }

    public int getSummaryTurnover() {
        return summaryTurnover;
    }

    public HashMap<String, String> toExport(){
        HashMap<String, String> values = new HashMap<String, String>(){{
            put("name", getName());
            put("idividualTaxpayerNumber",idividualTaxpayerNumber);
            put("marketCount", Integer.toString(marketCount));
            put("summaryCreditLine", Integer.toString(summaryCreditLine));
            put("deferredPayment", Integer.toString(deferredPayment));
            put("summaryTurnover", Integer.toString(summaryTurnover));
        }};

        return values;
    }

    /**
     * Check for valid individual taxpayer number
     * @param idividualTaxpayerNumber Individual taxpayer number
     * @return "True" if is number
     */
    private boolean isCorrectITN(String idividualTaxpayerNumber){
        if (idividualTaxpayerNumber.matches("\\d+")){
            return true;
        }
        return false;
    }
}
