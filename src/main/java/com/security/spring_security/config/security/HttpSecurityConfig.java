package com.security.spring_security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessMagConfig ->  sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(daoAuthProvider)
                .authorizeHttpRequests( authRequestConfig -> {

                    //Estos no estan autenticados
                    authRequestConfig.requestMatchers(HttpMethod.POST,"/costumers").permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();

                    //Todos los demas request si deben de estar autehnticados
                    authRequestConfig.anyRequest().authenticated();

                })
                .build();

    }

}
