package me.t.kaurami.service.converter;

import org.springframework.stereotype.Service;

@Service(value = "ITNConverter")
public class IndividualTaxpayerNumberConverter {

    private String convert(String individualTaxpayerNumber){
        if (isCorrectITN(individualTaxpayerNumber)){
            return individualTaxpayerNumber;
        }
        return "406";
    }

    private boolean isCorrectITN(String idividualTaxpayerNumber){
        if (idividualTaxpayerNumber.matches("\\d+")){
            return true;
        }
        return false;
    }
}
