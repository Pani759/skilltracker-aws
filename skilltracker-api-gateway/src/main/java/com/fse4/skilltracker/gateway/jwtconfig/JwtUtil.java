package com.fse4.skilltracker.gateway.jwtconfig;


import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fse4.skilltracker.gateway.exception.JwtTokenMalformedException;
import com.fse4.skilltracker.gateway.exception.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public Claims getClaims(final String token) {
		try {
			Claims body = extractAllClaims(token);
					//Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		try {
			//extractAllClaims(token); 		
			Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token);			
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}

	public String extractUsername(String token) { 
		return extractClaim(token, Claims::getSubject); 
	} 

	public Date extractExpiration(String token) { 
		return extractClaim(token, Claims::getExpiration); 
	} 

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
		final Claims claims = extractAllClaims(token); 
		return claimsResolver.apply(claims); 
	} 

	private Claims extractAllClaims(String token) { 
		return Jwts 
				.parserBuilder() 
				.setSigningKey(getSignKey()) 
				.build() 
				.parseClaimsJws(token) 
				.getBody(); 
	} 

	private Boolean isTokenExpired(String token) { 
		return extractExpiration(token).before(new Date()); 
	} 

	/*
	 * public Boolean validateToken(String token, UserDetails userDetails) { final
	 * String username = extractUsername(token); return
	 * (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); }
	 */
	private Key getSignKey() { 
		byte[] keyBytes= Decoders.BASE64.decode(jwtSecret); 
		return Keys.hmacShaKeyFor(keyBytes); 
	} 

}