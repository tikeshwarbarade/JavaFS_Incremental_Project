package com.edutech.progressive.jwt;

import com.edutech.progressive.entity.User;
import com.edutech.progressive.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    private UserRepository userRepository;

    private final String secret = "secretKey000000000000000000000000000000000000000000000000000000";
    private final int expiration = 86400; // 24 hours

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        User user = userRepository.findByUsername(username);

        Map<String, Object> claims = new HashMap<>();
        if (user != null) {
            claims.put("roles", user.getRole());
            claims.put("userId", user.getUserId());

            if ("STUDENT".equalsIgnoreCase(user.getRole())) {
                claims.put("studentId", user.getReferenceId());
            } else if ("TEACHER".equalsIgnoreCase(user.getRole())) {
                claims.put("teacherId", user.getReferenceId());
            }
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}