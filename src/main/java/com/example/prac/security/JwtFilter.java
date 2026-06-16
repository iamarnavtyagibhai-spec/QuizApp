package com.example.prac.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.prac.model.User;
import com.example.prac.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtservice;
    @Autowired
    UserRepository userrepo;

 @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String token = authHeader.substring(7);

        String email = jwtservice.extractEmail(token);
        System.out.println("TOKEN EMAIL = " + email);

        Optional<User> a = userrepo.findByEmail(email);
        System.out.println("USER FOUND = " + a.isPresent());

        if (a.isPresent() && jwtservice.isTokenValid(token)) {

            User user = a.get();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);
              System.out.println("AUTH SET SUCCESSFULLY");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    filterChain.doFilter(request, response);
}
}