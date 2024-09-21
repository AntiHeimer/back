package capstone.Antiheimer.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Slf4j
@Component
public class JwtTokenUtil {

    private final Key key;
    private final long exp;

    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.exp}") long exp) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.exp = exp;
    }

    public String generateToken(String memberUuid) {

        log.info("[JWT] 토큰 생성 시작");

        Claims claims = Jwts.claims();
        claims.put("memberUuid", memberUuid);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUuid(String token) {

        return parseClaims(token)
                .get("uuid")
                .toString();
    }

    public void validateToken(String token) {

        try {
            log.info("[JWT] 토큰 유효성 확인");

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SignatureException | SecurityException | MalformedJwtException e) {
            log.info("[JWT] Invalid JWT signature, 유효하지 않는 JWT 서명 입니다");
            throw new IllegalArgumentException("유효하지 않는 JWT 서명");
        } catch (ExpiredJwtException e) {
            log.info("[JWT] Expired JWT token, 만료된 JWT token 입니다");
            throw new IllegalArgumentException("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            log.info("[JWT] Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다");
            throw new IllegalArgumentException("지원하지 않는 JWT 토큰");
        } catch (IllegalArgumentException e) {
            log.info("[JWT] JWT claims is empty, 잘못된 JWT 토큰 입니다");
            throw new IllegalArgumentException("잘못된 JWT 토큰");
        }
    }

    public void validateHeader(String header) {

        log.info("[JWT] 헤더 유효성 확인");

        if (header == null) {
            log.info("[JWT] null 헤더");
            throw new IllegalArgumentException("null 헤더");
        }
        if (!header.startsWith("Bearer ")) {
            log.info("[JWT] 유효하지 않은 Bearer");
            throw new IllegalArgumentException("Bearer 오류");
        }
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
