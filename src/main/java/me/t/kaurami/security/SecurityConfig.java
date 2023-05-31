package me.t.kaurami.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private Environment environment;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("h2")
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("deckov")
                .password(passwordEncoder().encode("kKu7fNds"))
                .roles("ADMIN", "CREDIT_CONTROLLER")
                .build());
        manager.createUser(User
                .withUsername(environment.getProperty("actuator-manager.username"))
                .password(passwordEncoder().encode(environment.getProperty("actuator-manager.password")))
                .roles("ACTUATOR")
                .build());
        return manager;
    }

    @Bean
    @Profile("postgres")
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.createUser(User
                .withUsername(environment.getProperty("actuator-manager.username"))
                .password(passwordEncoder().encode(environment.getProperty("actuator-manager.password")))
                .roles("ACTUATOR")
                .build());
        return manager;
    }

    @Bean
    @Profile("!oauth2Security")
    public SecurityFilterChain webFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/agents").hasAnyAuthority("READ_DATA", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers(HttpMethod.GET, "/clients").hasAnyAuthority("READ_DATA", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers(HttpMethod.POST, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN")
                .antMatchers(HttpMethod.GET, "/requests").hasAnyAuthority("READ_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers(HttpMethod.POST, "/requests").hasAnyAuthority("CREATE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers(HttpMethod.PUT, "/requests").hasAnyAuthority("CHANGE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers(HttpMethod.PATCH, "/requests").hasAnyAuthority("CHANGE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers(HttpMethod.DELETE, "/requests").hasAnyAuthority("DELETE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER")
                .antMatchers("/db-console/**").hasRole("ADMIN")
                .antMatchers("/adminmodule/**").hasRole("ADMIN")
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .anyRequest().permitAll()
                .and().csrf().ignoringAntMatchers("/db-console/**", "/api/**", "/management/**")
                .and().headers().frameOptions().sameOrigin()
                .and().httpBasic()
                .and().logout().logoutSuccessUrl("/")
                .and().build();
    }

    @Bean
    @Profile("oauth2Security")
    SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(authorizationManager ->
                authorizationManager
                        .antMatchers(HttpMethod.GET, "/agents").hasAnyAuthority("READ_DATA", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_readData")
                        .antMatchers(HttpMethod.GET, "/clients").hasAnyAuthority("READ_DATA", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_readData")
                        .antMatchers(HttpMethod.POST, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN", "SCOPE_changeData")
                        .antMatchers(HttpMethod.PATCH, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN", "SCOPE_changeData")
                        .antMatchers(HttpMethod.PUT, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN", "SCOPE_changeData")
                        .antMatchers(HttpMethod.DELETE, "/agents", "/clients").hasAnyAuthority("CHANGE_DATA", "ADMIN", "SCOPE_changeData")
                        .antMatchers(HttpMethod.GET, "/requests").hasAnyAuthority("READ_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_readRequest")
                        .antMatchers(HttpMethod.POST, "/requests").hasAnyAuthority("CREATE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_createRequest")
                        .antMatchers(HttpMethod.PUT, "/requests").hasAnyAuthority("CHANGE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_changeRequest")
                        .antMatchers(HttpMethod.PATCH, "/requests").hasAnyAuthority("CHANGE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_changeRequest")
                        .antMatchers(HttpMethod.DELETE, "/requests").hasAnyAuthority("DELETE_REQUEST", "ROLE_ADMIN", "ROLE_CREDIT_CONTROLLER", "SCOPE_deleteIngredients")
                        .antMatchers("/db-console/**").hasRole("ADMIN")
                        .antMatchers("/adminmodule/**").hasRole("ADMIN")
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                        .anyRequest().permitAll()
        ).oauth2ResourceServer(oauth2 -> oauth2.jwt())
                .csrf().ignoringAntMatchers("/db-console/**", "/api/**", "/management/**")
                .and().headers().frameOptions().sameOrigin()
                .and().httpBasic()
                .and().logout().logoutSuccessUrl("/")
                .and().build();
    }
}
