package me.t.kaurami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;

@ImportResource("app-context.xml")
@SpringBootApplication
public class SpringDemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

}
