package me.t.kaurami.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import me.t.kaurami.service.converter.IndividualTaxpayerNumberConverter;
import me.t.kaurami.service.converter.NameConverter;

@Service(value = "tradePointFactory")
public class TradePointFactory {
    private NameConverter nameConverter;
    private IndividualTaxpayerNumberConverter ITNConverter;

    @Autowired
    @Qualifier(value = "nameConverter")
    public void setNameConverter(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }

    @Autowired
    @Qualifier(value = "ITNConverter")
    public void setITNConverter(IndividualTaxpayerNumberConverter ITNConverter) {
        this.ITNConverter = ITNConverter;
    }
}
