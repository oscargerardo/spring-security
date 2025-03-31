package com.security.spring_security.config.security.filter;

import com.security.spring_security.exception.ObjectNotFoundException;
import com.security.spring_security.persistence.entity.security.User;
import com.security.spring_security.service.UserService;
import com.security.spring_security.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("ENTRO EN EL FILTRO");
        // 1. Obtenemos el encabezado authorization

        String authorizationHeader = request.getHeader("Authorization");
        if( !StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ") ){
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Obtener el jwt desde el header

        String jwt = authorizationHeader.split(" ")[1];

        // 3. Obtener el usuario desde el token (valida formato, firma y expiracion del token)

        String userName =  jwtService.extractUserName(jwt);

        // 4. Setear objeto Authentication en SecurityContextHolder
        User user =  userService.findByUsername(userName)
                .orElseThrow( () -> new ObjectNotFoundException("User not found. Username: " + userName) );

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 5. Ejecutar resto de filtros
        filterChain.doFilter(request, response);
    }
}
