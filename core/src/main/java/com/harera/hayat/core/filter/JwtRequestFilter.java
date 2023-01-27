package com.harera.hayat.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.harera.hayat.core.model.user.User;
import com.harera.hayat.core.repository.UserRepository;
import com.harera.hayat.core.service.user.auth.JwtUtils;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtRequestFilter(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        Long userId = null;
        String jwt = null;
        try {
            if (authorizationHeader == null) {
                throw new JwtException("Authorization header is null");
            }

            if (!authorizationHeader.startsWith("Bearer ")) {
                throw new JwtException("Authorization header is null");
            }

            jwt = authorizationHeader.substring(7);
            userId = jwtUtils.extractUserId(jwt);

            if (userId == null) {
                throw new JwtException("Invalid token");
            }

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                return;
            }

            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                throw new JwtException("User not found");
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null,
                                            user.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
            request.getSession().setAttribute("loggedUser", userId);
        } catch (JwtException ex) {
            log.error(ex.getLocalizedMessage());
        }
        chain.doFilter(request, response);
    }
}
