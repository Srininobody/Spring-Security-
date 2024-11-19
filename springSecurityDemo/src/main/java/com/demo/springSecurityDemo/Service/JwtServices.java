package com.demo.springSecurityDemo.Service;

import com.demo.springSecurityDemo.Entity.MyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServices {

    private  String secretkey  = null;
    public String generateToken(MyUser user) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuer("DCB")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*10*1000))
                .and()
                .signWith(generatedKey())
                .compact();
    }

    private SecretKey generatedKey() {
       byte [] decode =  Decoders.BASE64.decode(getSecretkey());
       return Keys.hmacShaKeyFor(decode);

    }

    public String getSecretkey()
    {
      return   secretkey ="789ff73cb8173fa16e4cdb15f984b24680b35c82bde84e5b82f06d7ea14dc572b" +
                "d29c1cbe49e84e5fbcf037e0bd3ee95f0594844425a705a1ca926c96753b00efe31105" +
                "b8ac2fa0465590cb4282338e0387924f681a4b7b00ba0b50338f3585b7bac71f6732a235bfc" +
                "30de3351ca5bbac223b91be9ee8e1cb8e9f3a6c32733175f2b810c16ad3d0327b4cb19dcf16e" +
                "6e139ce33b478561417b24b0cc073520456d90b171fe434fd3640f7e6e5a43d82b024efcd6" +
                "861915564d8afdb60acdfe0681e866d14e65b7b44cfcacffb76f6188996850bb31d13ffc9432" +
                "786fbae43511a47d63e1217fe2fd645fe40127c8aed8d77cac30641f8929095bbdd8f0eaa670";
    }

    public String extractUserName(String token) {

        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractClaims(token);
        return  claimResolver.apply(claims);
    }
    private Claims extractClaims(String token)
    {
      return   Jwts.parser()
                .verifyWith(generatedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final  String userName = extractUserName(token);
        System.out.println("token validate userName ="+userName);
        System.out.println("token validate userName ="+userDetails.getUsername());
        System.out.println(isTokenExpired(token));
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token)) ;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
