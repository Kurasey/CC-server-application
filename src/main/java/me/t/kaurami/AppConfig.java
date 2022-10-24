package me.t.kaurami;

import me.t.kaurami.service.setting.ReportSetting;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.*;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
@Lazy(true)
public class AppConfig {

    @Bean
    public String nameTrimmingPattern(){
        return "^.+ИП|^.+ООО|^.+ПАО|^.+ЗАО|^.+МУП|^.+ГБУ|^.+СПК|^.+ОАО";
    }

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }


/*
    @Bean
    public ReportSetting volumeFormingSetting(){
        ReportSetting setting = new ReportSetting();
        setting.setColumnsReport(Arrays.asList(
                "Наименование",
                "Нал./Б/н ( в т.ч. накладные белые или черные)",
                "ТО(за последние три месяца), руб.",
                "Общий Лимит, руб.",
                "Отсрочка",
                "ИНН",
                "Оборачиваемость, дн",
                "Рент, %",
                "Комментарии КК",
                "Комментариии СБ")
        );
        setting.setNumericFormat(Arrays.asList(2,3));
        setting.setSheetName("Список");
        setting.setMathingFieldsAndColumns(new HashMap<String, Integer>(){{
            put("name",0);
            put("summaryTurnover",2);
            put("summaryCreditLine",3);
            put("deferredPayment",4);
            put("idividualTaxpayerNumber",5);
        }});
        setting.setValueToNumeric(Arrays.asList(2,3,4,5));
        return setting;
    }

    @Bean
    public ReportSetting ckeckingRelationship(){
        ReportSetting setting = new ReportSetting();
        setting.setColumnsReport(Arrays.asList(
                "Наименование",
                "ИНН",
                "Точки с КУ",
                "Предоплатные точки",
                "Не действующие точки",
                "Связанные точки",
                "Связанные точки c КУ",
                "Связанные предоплатные точки",
                "Связанные не действующие",
                "Хозяева сети",
                "Рекомендации"
        ));
        setting.setNumericFormat(Arrays.asList(2,3,4,5,6,7,8));
        setting.setSheetName("Список");
        setting.setMathingFieldsAndColumns(new HashMap<String, Integer>(){{
            put("name",0);
            put("individualTaxpayerNumber",1);
            put("validMarketsWithCredit",2);
            put("validMarketsWithoutCredit",3);
            put("notValidMarkets",4);
            put("asociatedClient",5);
            put("associatedWithCredit", 6);
            put("associatedWithoutCredit", 7);
            put("associatedNotValidMarket", 8);
            put("associatedNames",9);
            put("recomendation", 10);


        }});
        setting.setValueToNumeric(Arrays.asList(2,3,4,5,6,7,8));
        return setting;
    }*//*old*/


}
