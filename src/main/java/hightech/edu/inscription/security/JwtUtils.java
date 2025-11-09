package hightech.edu.inscription.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import hightech.edu.inscription.service.UserDetailsImpl;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        // If the secret is base64 encoded, decode it
        if (jwtSecret != null && jwtSecret.length() >= 32) {
            try {
                byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
                return Keys.hmacShaKeyFor(keyBytes);
            } catch (IllegalArgumentException e) {
                // If not base64, use the string directly (ensure it's long enough)
                if (jwtSecret.length() < 32) {
                    throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
                }
                return Keys.hmacShaKeyFor(jwtSecret.getBytes());
            }
        } else {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }
    }

    // Génération du token JWT à partir de l'utilisateur authentifié
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // username comme "subject"
                .setIssuedAt(new Date()) // date de création
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // signature
                .compact();
    }

    // Récupérer le username à partir du token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valider le token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }
}