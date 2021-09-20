package com.maplecheater.filter;

import com.maplecheater.domain.entity.Role;
import com.maplecheater.security.UserAuthentication;
import com.maplecheater.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthenticationFilter extends BasicAuthenticationFilter {

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                AuthenticationService authenticationService) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader("Authorization");

        if(authorization != null) {
            String accessToken = authorization.substring("Bearer ".length());
            Long userId = authenticationService.parseToken(accessToken);

            List<Role> roles = authenticationService.getRoles(userId);

            Authentication authentication = new UserAuthentication(userId, roles);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
