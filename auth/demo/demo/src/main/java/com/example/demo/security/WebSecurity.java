package com.example.demo.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
   @Order(2)
public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    System.out.println(("HTTP OLHA SO:"));
    System.out.println(("\n\n\n"));
    System.out.println(http);
    http
    //.cors(Customizer.withDefaults())
//     .formLogin(form -> form
//     .loginPage("/login")           // sua página customizada
//     .loginProcessingUrl("/login")  // onde o POST cai
//     .permitAll()
// )
//.formLogin(Customizer.withDefaults())
    .csrf(csrf -> csrf.disable())
    
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .authorizeHttpRequests( authorize -> authorize
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/login", "/error").permitAll()
               .requestMatchers("/auth/validate").permitAll() // nginx chama internamente
               .requestMatchers("/auth/login").permitAll()  
               .requestMatchers("/auth/register").permitAll()  
            .anyRequest().authenticated()
        );
        // .exceptionHandling(e -> e.authenticationEntryPoint(
        //     (request, response, authException) -> {
        //         System.out.println("NAO AUTENTICADO NO AUTHORIZE: " + authException.getMessage());
        //         response.sendError(401);
        //     }));
         //.formLogin(Customizer.withDefaults());
  
        
    return http.build();
}

// @Bean
// public CorsConfigurationSource corsConfigurationSource() {
//     CorsConfiguration config = new CorsConfiguration();
//     config.setAllowedOrigins(List.of("http://localhost:5173"));
//     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//     config.setAllowedHeaders(List.of("*"));
//     config.setAllowCredentials(true);

//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     source.registerCorsConfiguration("/**", config);
//     return source;
// }

 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
