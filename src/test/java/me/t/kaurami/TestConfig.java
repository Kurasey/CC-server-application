package me.t.kaurami;

import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Request;
import me.t.kaurami.service.setting.ReportFormatHolder;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "me.t.kaurami.*")
public class TestConfig {

 /*   @Bean
    Map<String, Map<String, List<String>>> fields() {
*//*        Map<String, Map<String, List<String>>> fields;
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("app-context.xml");
        context.refresh();
        fields = context.getBean("fields", Map.class);
        context.close();
        return fields;*//*
    }*/
    @Bean
    Map<String, ReportFormatHolder> formatHolders(){
        Map<String, ReportFormatHolder> formatHolders;
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("app-context.xml");
        context.refresh();
        formatHolders = context.getBean("formatHolders", Map.class);
        context.close();
        return formatHolders;
    }
}
