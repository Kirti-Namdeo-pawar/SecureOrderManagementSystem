package com.AuthService.AuthService.service;

import com.AuthService.AuthService.Entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import com.AuthService.AuthService.Entities.User;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
//@Service
public class AuthUtil {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    private SecretKey getsecretKey(){

        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role",user.getRole().name()) // user.getRole() → enum   & .name() → converts enum → "ADMIN"
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getsecretKey())
                .compact();

}


    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
public String extractRole(String token){
        return getClaims(token).get("role", String.class);
}
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /*Spring Security authenticates via username

UserDetailsService expects username

Best practice:

sub → username

custom claims → userId, role*/

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getsecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
/*JWT validation includes:

Signature verification

Expiry check

Subject extraction*/