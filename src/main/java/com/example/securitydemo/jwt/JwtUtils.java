package com.example.securitydemo.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest httpServletRequest){

        String bearerToken = httpServletRequest.getHeader("Authorization");

        logger.debug("Authorization Token {}", bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }


    public String generateTokenFromUsername(UserDetails userDetails){

        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime()+ jwtExpirationMs))
                .signWith(key())
                .compact();

    }

    public String getUsernameFromToken(String token){

        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    public boolean validateJwtToken(String authToken){
        try{
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        }
        catch (MalformedJwtException e){
            logger.error("Invalid Jwt Token {} "+ e.getMessage());
        }
        catch (ExpiredJwtException e){
            logger.error("Jwt is expired {} "+ e.getMessage());
        }
        catch(UnsupportedJwtException e){
            logger.error("Jwt is unsupported {} "+ e.getMessage());
        }
        catch(IllegalArgumentException e){
            logger.error("Jwt claim string is empty "+ e.getMessage());
        }
        return false;
    }


}