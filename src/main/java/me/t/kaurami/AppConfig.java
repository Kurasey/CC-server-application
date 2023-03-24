package me.t.kaurami;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@Configuration
@Lazy(true)
public class AppConfig {

/*    @Bean
    public String nameTrimmingPattern(){         //delete
        return "^.+ИП|^.+ООО|^.+ПАО|^.+ЗАО|^.+МУП|^.+ГБУ|^.+СПК|^.+ОАО";
    }*/

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }
}
