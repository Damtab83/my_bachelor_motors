package com.damtab.my_bachelor_motors.configuration.jwt;

import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final UserCustomDetailsService userCustomDetailsService;
    private final JwtUtils jwtUtils;

    //Verify authentication is valid with token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String headerAuth = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if(headerAuth != null && headerAuth.startsWith("Bearer ")
        && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                jwt = headerAuth.substring(7);
                email = jwtUtils.extractEmail(jwt);
                UserDetails userDetails = userCustomDetailsService.loadUserByUsername(email);

                if (jwtUtils.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e){
                log.debug("Token JWT invalide ou utilisateur introuvable : {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/api/voiture")
                || path.startsWith("/api/authentification/")
                || path.startsWith("/api/image-voiture");
    }
}
