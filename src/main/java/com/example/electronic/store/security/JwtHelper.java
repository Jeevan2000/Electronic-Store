package com.example.electronic.store.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    //1. token validity

    public static final long TOKEN_VALIDITY = 5*60*60*1000;

    //2.secret key
    SignatureAlgorithm hs512 = SignatureAlgorithm.HS512;

    public static final String SECRET_KEY = "ahdsfjklhadfhoilksdflksdflkdsnflkndslkfnlkdsnflkndslknflksdnflsdnflkndslkfnewrthfoeswdnvflknsdofvjewpfsdjkbhsdajkfhsdkfjahfasnbcoidghewoifhgoehf";

    public String getUsernameFromToke(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimFromToken(String token)
    {
       // SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), hs512.getJcaName());
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();
    }
    
    public Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

}
