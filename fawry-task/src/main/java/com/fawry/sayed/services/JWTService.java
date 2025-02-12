package com.fawry.sayed.services;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fawry.sayed.security.filters.CustomUserPasswordAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;	
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JWTService {
	
    public static final String COOKIE_NAME = "JWt";

	@Value("${security.jwts.secret-key}")
    private String secretKey;

    @Value("${security.jwts.expiration-time}")
    private int jwtExpiration;
    
    
    public int getJwtExpiration() {
		return jwtExpiration;
	}

	public void setJwtExpiration(int jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
	}

	public String generateToken(Map<String, Object> extraClaims, String userName) {
        return buildToken(extraClaims, userName, jwtExpiration);
    }
    
    
    private String buildToken(
            Map<String, Object> extraClaims,
            String userName,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }    
    
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
    public static Cookie removeTokenFromCookie(String token) {
		Cookie jwtCookie = new Cookie("JWt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); 
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        return jwtCookie;
	}
    
    public static String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> COOKIE_NAME.
                    		equals(cookie.getName())) // Match the cookie name
                    .map(Cookie::getValue) // Extract the cookie value (JWT token)
                    .findFirst()
                    .orElse(null); // Return null if not found
        }
        return null;
    }
}
