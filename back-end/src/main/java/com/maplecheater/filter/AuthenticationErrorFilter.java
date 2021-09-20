package com.maplecheater.filter;

import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.InvalidTokenException;
import com.maplecheater.exception.TokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationErrorFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } catch (ExpiredJwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } catch (MalformedJwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}