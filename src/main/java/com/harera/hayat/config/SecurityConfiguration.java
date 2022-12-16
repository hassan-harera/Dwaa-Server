package com.harera.hayat.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harera.hayat.filter.AuthFilter;
import com.harera.hayat.model.exception.ApiError;
import com.harera.hayat.model.exception.GlobalMessage;
import com.harera.hayat.repository.GlobalMessageRepository;
import com.harera.hayat.security.JwtRequestFilter;
import com.harera.hayat.security.JwtService;
import com.harera.hayat.service.user.UserService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,
                jsr250Enabled = true)
public class SecurityConfiguration {

    private final AuthFilter authFilter;
    private final GlobalMessageRepository globalMessageRepository;
    private final ObjectMapper mapper;
    private final String UNAUTHORIZED = "unauthorized";

    public SecurityConfiguration(AuthFilter authFilter,
                    GlobalMessageRepository globalMessageRepository,
                    ObjectMapper mapper) {
        this.authFilter = authFilter;
        this.globalMessageRepository = globalMessageRepository;
        this.mapper = mapper;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                    PasswordEncoder bCryptPasswordEncoder, UserService userDetailService)
                    throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                        .userDetailsService(userDetailService)
                        .passwordEncoder(bCryptPasswordEncoder).and().build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtRequestFilter requestFilter(JwtService authManager,
                    UserService userDetailService) {
        return new JwtRequestFilter(userDetailService, authManager);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                    JwtRequestFilter jwtRequestFilter) throws Exception {
        final String[] publicUris = { "/api/v1/login/**", "/api/v1/oauth/**",
                "/api/v1/signup/**", "/api/v1/cities/**", "/api/v1/states/**",
                "/api/v1/notifications/**", };
        httpSecurity.csrf().disable().authorizeRequests().antMatchers(publicUris)
                        .permitAll().antMatchers("/**").hasAnyAuthority("USER_ROLE").and()
                        .exceptionHandling()
                        .authenticationEntryPoint(this::authenticateExceptionHandler)
                        .and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .addFilterBefore(authFilter,
                                        UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private void authenticateExceptionHandler(HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse, AuthenticationException e)
                    throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setDisplayMessage(assignDisplayMessage(httpServletRequest));
        httpServletResponse.setCharacterEncoding("UTF-8");
        final PrintWriter writer = httpServletResponse.getWriter();
        writer.write(mapper.writeValueAsString(apiError));
        httpServletResponse.setContentType("application/json");

    }

    private String assignDisplayMessage(HttpServletRequest request) {
        final String LANG_HEADER = "language";
        final String header = request.getHeader(LANG_HEADER);
        String language = StringUtils.isNotEmpty(header) ? header : "en";
        final Optional<GlobalMessage> globalMessage = globalMessageRepository
                        .findByLanguageAndCode(language, UNAUTHORIZED);
        return globalMessage.get().getMessage();
    }
}
