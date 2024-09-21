package capstone.Antiheimer.util.jwt;

import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("[JWT] jwt filter 시작");

        try {
            // 요청 경로 가져오기
            String requestURI = request.getRequestURI();
            System.out.println("requestURI = " + requestURI);

            // 회원가입 경로에 대해서는 필터링을 건너뜁니다.
            if ("/".equals(requestURI) || "/favicon.ico".equals(requestURI) || "/signup".equals(requestURI) || "/login".equals(requestURI) || "/saveRelation/location".equals(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            log.info("[JWT] Header, Bearer 확인");
            String authorizationHeader = request.getHeader("Authorization");
            jwtTokenUtil.validateHeader(authorizationHeader);

            log.info("[JWT] 토큰 확인");
            String token = authorizationHeader.split(" ")[1];
            jwtTokenUtil.validateToken(token);

            String memberUuid = jwtTokenUtil.getUuid(token);
            Member member = memberRepository.findOneByUuid(memberUuid);

            if (member == null) {
                throw new IllegalArgumentException("유효하지 않은 uuid");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUuid(), null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            log.info("[JWT] 토큰 검증 완료");
            filterChain.doFilter(request, response);

        } catch (IllegalArgumentException e) {

            log.info("[JWT] Jwt 토큰 오류: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = "{\"statusCode\": 401, \"message\": \"" + e.getMessage() + "\"}";

            response.getWriter().write(jsonResponse);
        }
    }
}
