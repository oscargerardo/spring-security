package com.security.spring_security.config.security;

import com.security.spring_security.config.security.filter.JwtAuthenticationFilter;
import com.security.spring_security.persistence.util.RolPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessMagConfig ->  sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(HttpSecurityConfig::buildRequestMatchers)
                .build();

    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequestConfig) {
        //Estos no estan autenticados

        /*
        * AUthorizacion de productos
        */
        authRequestConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAuthority(RolPermission.READ_ALL_PRODUCTS.name());

        authRequestConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAuthority(RolPermission.READ_ONE_PRODUCT.name());

        authRequestConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolPermission.CREATE_ONE_PRODUCT.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAuthority(RolPermission.UPDATE_ONE_PRODUCT.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasAuthority(RolPermission.DISABLE_ONE_PRODUCT.name());

        /*
        * Authorizacion de categories
        */

        authRequestConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAuthority(RolPermission.READ_ALL_CATEGORIES.name());

        authRequestConfig.requestMatchers(HttpMethod.GET, "/categories/{categoriyId}")
                .hasAuthority(RolPermission.READ_ONE_CATEGORY.name());

        authRequestConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasAuthority(RolPermission.CREATE_ONE_CATEGORY.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriyId}")
                .hasAuthority(RolPermission.UPDATE_ONE_CATEGORY.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriyId}/disabled")
                .hasAuthority(RolPermission.DISABLE_ONE_CATEGORY.name());


        /*
         *  Profiles
         */
        authRequestConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAuthority(RolPermission.READ_MY_PROFILE.name());

        /*
        *  Authorizacion de enpints publicos
        */
        authRequestConfig.requestMatchers(HttpMethod.POST,"/custumers").permitAll();
        authRequestConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authRequestConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();

        //Todos los demas request si deben de estar autehnticados
        authRequestConfig.anyRequest().authenticated();
    }

}
