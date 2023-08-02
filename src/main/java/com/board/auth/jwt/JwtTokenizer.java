package com.board.auth.jwt;

import com.board.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtTokenizer {
    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("email", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = getTokenExpiration(getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

        return generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    public String delegateRefreshToken(Member member) {
        Date expiration = getTokenExpiration(getRefreshTokenExpirationMinutes());

        String subject = member.getEmail();
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

        return generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }

    public String generateAccessToken(Map<String, Object> claims, String subject, Date expiration, String baseEncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(baseEncodedSecretKey);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Calendar.getInstance().getTime()).setExpiration(expiration).signWith(key).compact();
    }

    public String generateRefreshToken(String subject, Date expiration, String baseEncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(baseEncodedSecretKey);
        return Jwts.builder().setSubject(subject).setIssuedAt(Calendar.getInstance().getTime()).setExpiration(expiration).signWith(key).compact();
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);

        return calendar.getTime();
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Long extractMemberIdFromAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = getClaims(accessToken, encodeBase64SecretKey(getSecretKey()));
            return claims.getBody().get("memberId", Long.class);
        } catch (JwtException e) {
            throw new AuthenticationServiceException("Invalid or expired access token");
        }
    }
}
