package net.ausiasmarch.cineServerJWT.helper;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTHelper {
    
    private static final String SECRET = "CLAVEsecretaSERVERLuis";
    private static final String ISSUER = "CineServer-Luis";

    private static SecretKey secretKey() {
        return Keys.hmacShaKeyFor((SECRET+ISSUER+SECRET).getBytes());
    }

    public static String generateJWT(String id, String name, String tipousuario) {
        Date currentTime = Date.from(Instant.now());
        Date expirationTime = Date.from(Instant.now().plus(Duration.ofSeconds(9600)));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer(ISSUER)
                .setIssuedAt(currentTime)
                .setExpiration(expirationTime)
                .claim("id", id)
                .claim("name", name)
                .claim("tipousuario", tipousuario)
                .signWith(secretKey())
                .compact();
    }

    public static String validateJWT (String JWT) {
        Jws<Claims> headerClaimJwt = Jwts
                                    .parserBuilder()
                                    .setSigningKey(secretKey())
                                    .build()
                                    .parseClaimsJws(JWT);
        
        Claims claims = headerClaimJwt.getBody();
        if (!claims.getIssuer().equals(ISSUER)) {
            throw new JwtException("JWT no válido");
        }
        return claims.get("name", String.class);
    }
}
