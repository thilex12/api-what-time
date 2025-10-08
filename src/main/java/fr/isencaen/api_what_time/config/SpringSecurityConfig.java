package fr.isencaen.api_what_time.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.build();
        return http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/v1/events").permitAll();
//                    auth.requestMatchers("/v1/accounts").hasRole("USER");
                    //auth.requestMatchers("/v1/accounts/test").permitAll();
                    auth.requestMatchers("/v1/accounts").hasRole("USER");
                    auth.anyRequest().authenticated();
                }
        )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()).build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
