package agh.edu.pl.healthmonitoringsystem.api.common;

import agh.edu.pl.healthmonitoringsystem.domain.model.response.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.SECRET_KEY;

public class JwtUtil {
    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.role().name());
        claims.put("email", user.email());
        claims.put("id", user.id());
        claims.put("password", user.password());

        return createToken(claims, user.id().toString());
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

