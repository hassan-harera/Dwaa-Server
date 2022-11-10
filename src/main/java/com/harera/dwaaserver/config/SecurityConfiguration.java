package com.harera.dwaaserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.harera.dwaaserver.security.JwtRequestFilter;
import com.harera.dwaaserver.security.JwtService;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {


    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            PasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailService
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtRequestFilter requestFilter(JwtService authManager, UserDetailsService userDetailService) {
        return new JwtRequestFilter(userDetailService, authManager);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        return http.csrf().disable()
                .addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/login", "/api/v1/signup").permitAll()
                .antMatchers("/api/v1/unit/**").permitAll()
                .antMatchers("/api/v1/packaging/**").permitAll()
                .antMatchers("/api/v1/announcements/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }
}
