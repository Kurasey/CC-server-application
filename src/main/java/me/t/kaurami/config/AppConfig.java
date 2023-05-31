package me.t.kaurami.config;

import me.t.kaurami.web.controller.AvailableFromAdminPage;
import me.t.kaurami.web.controller.AvailableFromHomepage;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ImportResource("app-context.xml")
public class AppConfig {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }
}
