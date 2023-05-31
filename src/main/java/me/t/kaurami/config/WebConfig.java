package me.t.kaurami.config;

import me.t.kaurami.web.controller.AvailableFromAdminPage;
import me.t.kaurami.web.controller.AvailableFromHomepage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/adminmodule/homepage").setViewName("adminhome");
    }

    @Bean
    public List<AvailableFromHomepage> availableFromHomepageList(List<AvailableFromHomepage> availableFromHomepageList) {
        return availableFromHomepageList;
    }

    @Bean
    public List<AvailableFromAdminPage> availableFromAdminPageList(List<AvailableFromAdminPage> availableFromAdminPageList) {
        return availableFromAdminPageList;
    }

    @Bean
    AvailableFromHomepage adminModuleReference() {
        return new AvailableFromHomepage() {
            @Override
            public String getName() {
                return "Администрирование";
            }

            @Override
            public String getReference() {
                return "/adminmodule/homepage";
            }
        };
    }
}
