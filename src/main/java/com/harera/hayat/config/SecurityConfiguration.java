package com.harera.hayat.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harera.hayat.model.exception.ApiError;
import com.harera.hayat.model.exception.GlobalMessage;
import com.harera.hayat.repository.GlobalMessageRepository;
import com.harera.hayat.service.user.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final GlobalMessageRepository globalMessageRepository;
    private final ObjectMapper mapper;
    private final String UNAUTHORIZED = "unauthorized";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(GlobalMessageRepository globalMessageRepository,
                    ObjectMapper mapper, UserService userService,
                    PasswordEncoder passwordEncoder) {
        this.globalMessageRepository = globalMessageRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        final String[] publicUris = { "/api/v1/login", "/api/v1/oauth/**",
                "/api/v1/signup/**", "/api/v1/cities/**", "/api/v1/states/**",
                "/api/v1/notifications/**", };

        httpSecurity.csrf().disable().authorizeRequests().antMatchers(publicUris)
                        .permitAll().anyRequest().authenticated().and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // permit all
        auth.inMemoryAuthentication().withUser("admin")
                        .password(passwordEncoder.encode("admin")).roles("ADMIN");
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
