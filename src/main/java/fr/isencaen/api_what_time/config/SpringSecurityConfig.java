package fr.isencaen.api_what_time.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.build();
        return http.authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/v1/events").permitAll();
                            //auth.requestMatchers("/v1/events").hasAnyRole("USER", "ADMIN");

                            //                    auth.requestMatchers("/v1/accounts").hasRole("USER");
                            //auth.requestMatchers("/v1/accounts/test").permitAll();
                            auth.requestMatchers("/v1/accounts").permitAll();
                            auth.requestMatchers("/v1/accounts/me").hasAnyRole("USER", "ADMIN");

                            auth.requestMatchers("/v1/locations").hasAnyRole("USER", "ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/v1/locations").hasAnyRole("USER", "ADMIN");
                            auth.requestMatchers(HttpMethod.PUT, "/v1/locations").hasAnyRole("USER", "ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/v1/locations").hasAnyRole("USER", "ADMIN");

                            auth.requestMatchers("/v1/notifications").hasAnyRole("USER", "ADMIN");

                            auth.requestMatchers("/v1/accounts/me").hasAnyRole("USER", "ADMIN");
                            auth.requestMatchers("/v1/events").hasAnyRole("USER", "ADMIN");
                            auth.requestMatchers(HttpMethod.GET, "v1/admin-events").hasRole("ADMIN");
                            auth.requestMatchers(HttpMethod.GET, "v1/admin-accounts").hasRole("ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/v1/tags").hasRole("ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/v1/tags").hasRole("ADMIN");
                            auth.requestMatchers(HttpMethod.GET, "/v1/tags").hasAnyRole("USER", "ADMIN");

                            auth.anyRequest().authenticated();
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource( configBis -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList(
                            "http://localhost:3000",
                            "http://localhost:8080",
                            "https://api.thilex.net"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(Arrays.asList("*"));
                    return config;
        }))
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customEntryPoint())).build();
    }

    @Bean
    public AuthenticationEntryPoint customEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
