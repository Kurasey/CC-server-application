package me.t.kaurami.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

@Configuration
@Lazy(true)
@ImportResource("app-context.xml")
public class AppConfig {

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }

    @Bean
    @Profile("prod")
    DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .username("postgres")
                .password("kKu7fNds")
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/postgres?currentSchema=ccModule")
                .build();
    }


}
