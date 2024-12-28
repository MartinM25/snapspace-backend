package com.snapspace.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  // Secret key for signing tokens
  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Token expiration time (24 hours)
  private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

  //  Generate a JWT token.
  public static String generateToken(String userId, String username) {
    String token = Jwts.builder()
        .setSubject(userId)
        .claim("username", username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SECRET_KEY)
        .compact();
    System.out.println("Generated token for user: " + username);
    return token;
  }

  //  Validate token
  public static boolean validateToken(String token, UserDetails userDetails) {
    try {
      Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
      System.out.println("Token is valid for user: " + userDetails.getUsername());
      return true;
    } catch (Exception e) {
      System.err.println("Invalid token: " + e.getMessage());
      return false;
    }
  }

  // Extract the user ID from a JWT token.
  public static String extractUserId(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  // Extract the username from a JWT token.
  public static String extractUsername(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseClaimsJws(token)
          .getBody();
      String username = claims.get("username", String.class);
      System.out.println("Extracted username from token: " + username);
      return username;
    } catch (Exception e) {
      System.err.println("Error extracting username from token: " + e.getMessage());
      throw e;
    }
  }
}
