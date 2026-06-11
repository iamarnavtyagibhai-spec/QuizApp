package com.example.prac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Autowired
    JwtFilter jwtfilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
           .csrf(csrf->csrf.disable())
           .authorizeHttpRequests(auth->auth.requestMatchers("/auth/**").permitAll()
           .requestMatchers("/owner/**").hasRole("OWNER")
            .requestMatchers("/admin/**").hasAnyRole("ADMIN","OWNER")
            .requestMatchers("/user/**").hasAnyRole("USER","ADMIN","OWNER")
           .anyRequest().authenticated())
           .addFilterBefore(
            jwtfilter,
            UsernamePasswordAuthenticationFilter.class
    );
     return http.build();


    }

}
