package com.snapspace.security;

import com.snapspace.service.UserService;
import com.snapspace.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    // Retrieve the Authorization header
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;

    try {
      // Check if the header starts with "Bearer"
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = JwtUtil.extractUsername(token);
      }

      // Log extracted token and username
      logger.info("Token: " + token);
      logger.info("Username from token: " + username);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        // Load the user details based on the username
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Validate token and set authentication
        if (JwtUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
          );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
          logger.warning("Token validation failed.");
        }
      }
    } catch (Exception e) {
      logger.severe("Error in JWT authentication: " + e.getMessage());
    }

    // Continue the filter chain
    filterChain.doFilter(request, response);
  }
}
