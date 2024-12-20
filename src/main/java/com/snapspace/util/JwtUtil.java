package com.snapspace.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

  // Secret key for signing tokens
  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Token expiration time (24 hours)
  private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

  //  Generate a JWT token.
  public static String generateToken(String userId, String username) {
    return Jwts.builder()
        .setSubject(userId) // Set the user ID as the subject
        .claim("username", username) // Include username as a claim
        .setIssuedAt(new Date()) // Set the issue date
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration
        .signWith(SECRET_KEY) // Sign with the secret key
        .compact();
  }

  //  Validate token
  public static boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseClaimsJws(token);
      return true; // Token is valid
    } catch (Exception e) {
      return false; // Token is invalid
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
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.get("username", String.class);
  }
}
