package com.maplecheater.configuration;

import com.maplecheater.filter.AuthenticationErrorFilter;
import com.maplecheater.filter.AuthenticationFilter;
import com.maplecheater.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.servlet.Filter;


@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Filter authenticationFilter = new AuthenticationFilter(
                authenticationManager(),
                authenticationService
        );

        Filter errorFilter = new AuthenticationErrorFilter();

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(errorFilter, AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }
}
