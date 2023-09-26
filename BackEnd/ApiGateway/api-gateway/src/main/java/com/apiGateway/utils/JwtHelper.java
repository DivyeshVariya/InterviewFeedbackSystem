package com.apiGateway.utils;

import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	private Logger logger=LoggerFactory.getLogger(JwtHelper.class);
	
	//requirement :
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private final String SECRET = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    public void validateToken(String token) {
		try {
			
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
            Claims tokenClaims = claims.getBody();
            logger.info("Token validation successful. Token claims: {}", tokenClaims);
            
        }catch (Exception ex) {
            logger.error("Jwt Token Exception : {}", ex.getMessage());
            throw new RuntimeException("Jwt Token Exception : {}"+ ex.getMessage());
        }
	}

	public String getRole(String token) {
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token).getBody();
		return (String) claims.get("role");
	}

	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
