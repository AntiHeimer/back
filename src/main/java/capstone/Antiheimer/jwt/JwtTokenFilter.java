package capstone.Antiheimer.jwt;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.repository.MemberRepository;
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

        log.info("filter 시작");

        try {
            // 요청 경로 가져오기
            String requestURI = request.getRequestURI();
            System.out.println("requestURI = " + requestURI);

            // 회원가입 경로에 대해서는 필터링을 건너뜁니다.
            if ("/signup".equals(requestURI) || "/login".equals(requestURI) || "/".equals(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            log.info("Header, Bearer 확인");
            String authorizationHeader = request.getHeader("Authorization");
            jwtTokenUtil.validateHeader(authorizationHeader);

            log.info("토큰 확인");
            String token = authorizationHeader.split(" ")[1];
            jwtTokenUtil.validateToken(token);

            String uuid = jwtTokenUtil.getUuid(token);
            Member member = memberRepository.findOneByUuid(uuid);

            if (member == null) {
                throw new IllegalArgumentException("유효하지 않은 uuid");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUuid(), null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        } catch (IllegalArgumentException e) {

            log.info("Jwt 토큰 오류: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = "{\"statusCode\": 401, \"message\": \"" + e.getMessage() + "\"}";

            response.getWriter().write(jsonResponse);
        }
    }
}
