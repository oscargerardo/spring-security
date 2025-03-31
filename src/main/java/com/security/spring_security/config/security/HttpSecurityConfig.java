package com.security.spring_security.config.security;

import com.security.spring_security.config.security.filter.JwtAuthenticationFilter;
import com.security.spring_security.config.security.handler.CustomAccessDeniedHandler;
import com.security.spring_security.config.security.handler.CustomAuthenticationEntryPoint;
import com.security.spring_security.persistence.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity // Para authorizacion en encabezados
//@EnableMethodSecurity //Para anotaciones en los metodos
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement( sessMagConfig ->  sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(HttpSecurityConfig::buildRequestMatchers)
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(customAccessDeniedHandler);
                })
                .build();
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequestConfig) {
        /*
        * AUthorizacion de productos
        */
        authRequestConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.READ_ALL_PRODUCTS.name());

//        authRequestConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
        authRequestConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9*]"))
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.READ_ONE_PRODUCT.name());

        
        authRequestConfig.requestMatchers(HttpMethod.POST, "/products")
                        .hasRole(RoleEnum.ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.CREATE_ONE_PRODUCT.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.UPDATE_ONE_PRODUCT.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasRole(RoleEnum.ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.DISABLE_ONE_PRODUCT.name());

        /*
        * Authorizacion de categories
        */

        authRequestConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.READ_ALL_CATEGORIES.name());

        authRequestConfig.requestMatchers(HttpMethod.GET, "/categories/{categoriyId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.READ_ONE_CATEGORY.name());

        authRequestConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(RoleEnum.ADMINISTRATOR.name());
               //.hasAuthority(RolPermission.CREATE_ONE_CATEGORY.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriyId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.UPDATE_ONE_CATEGORY.name());

        authRequestConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriyId}/disabled")
                .hasRole(RoleEnum.ADMINISTRATOR.name());
                //.hasAuthority(RolPermission.DISABLE_ONE_CATEGORY.name());


        /*
         *  Profiles
         */
        authRequestConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());
                //.hasAuthority(RolPermission.READ_MY_PROFILE.name());

        /*
        *  Authorizacion de enpints publicos
        */
        authRequestConfig.requestMatchers(HttpMethod.POST,"/custumers").permitAll();
        authRequestConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authRequestConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();

        //Todos los demas request si deben de estar autehnticados
        authRequestConfig.anyRequest().authenticated();
    }

    private static void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequestConfig) {
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
