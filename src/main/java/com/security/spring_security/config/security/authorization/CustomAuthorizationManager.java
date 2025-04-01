package com.security.spring_security.config.security.authorization;

import com.security.spring_security.persistence.entity.security.Operation;
import com.security.spring_security.persistence.repository.security.OperationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        //System.out.println(request.getRequestURL());
        //System.out.println(request.getRequestURI());
        
        String url = extractUrl(request);
        String httpMethod = request.getMethod();

        boolean isPublic = isPublic(url, httpMethod);

        return new AuthorizationDecision(isPublic);
    }

    private String extractUrl(HttpServletRequest request ) {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();

        url =  url.replace(contextPath, "");
        System.out.println(url);

        return url;
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoint = operationRepository.findByPublicAcces();
        boolean isPublic = publicAccessEndpoint.stream().anyMatch(operation -> {
            String basePath = operation.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath + operation.getPath() );
            Matcher matcher =  pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        });

        System.out.println("IS_PUBLIC: " + isPublic);
        return isPublic;
    }


}
