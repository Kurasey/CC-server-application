package me.t.kaurami.entities;

import java.util.HashMap;

public class MarownerVolumeForming extends Marowner implements Exportable {

    private String idividualTaxpayerNumber;
    private int marketCount;
    private int summaryCreditLine;
    private int deferredPayment;
    private int summaryTurnover;

    public MarownerVolumeForming(String name, String idividualTaxpayerNumber, int summaryCreditLine, int deferredPayment, int summaryTurnover) {
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

    public void addMarket(int summaryCreditLine, int deferredPayment, int summaryTurnover){
        this.marketCount += 1;
        this.summaryCreditLine += summaryCreditLine;
        this.deferredPayment = deferredPayment > this.deferredPayment ? deferredPayment : this.deferredPayment;
        this.summaryTurnover += summaryTurnover;
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

    private boolean isCorrectITN(String idividualTaxpayerNumber){
        if (idividualTaxpayerNumber.matches("\\d+")){
            return true;
        }
        return false;
    }
}
