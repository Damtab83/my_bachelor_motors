package com.damtab.my_bachelor_motors.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private long expirationTime;

    //Generate token including email
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    //Create token with
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Create Key with Hash Code and PassKey
    private Key getSignKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    //Validation without email to token
    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);

        return(email.equals(userDetails.getUsername()) && !isTokenExpiredtoken(token));
    }

    //Time to expired token
    private boolean isTokenExpiredtoken(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    //Validate time expiration
    private Date extractExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    //Extract email to token
    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    //Extract option of token to find single email
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extract all options of token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
