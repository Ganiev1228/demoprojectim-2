package com.example.project2.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.project2.entity.workers;

//import com.example.project2.entity.workers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <r> r extractClaim(String token, Function<Claims, r> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

//	public String generateToken(workers user) {
//	      HashMap<String , Object> dd = new HashMap<>();
//	      dd.put("ismi",user.getIsmi());
//	      dd.put("familiyasi",user.getFamiliyasi());Map<String, Object> extraClaims,
//	      return generateToken(dd, user);
//	}
	public String generateToken(UserDetails userdetails) {
		return Jwts.builder()
				// .setClaims(extraClaims)
				.setSubject(userdetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 60 * 24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, UserDetails userdetails) {
		final String username = extractUsername(token);
		return (username.equals(userdetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private java.security.Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
