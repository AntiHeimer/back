package capstone.Antiheimer.Jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    //JWTUtil 주입
    private final JwtUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    //비밀번호를 해싱
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable()) //csrf disable
                .formLogin((auth) -> auth.disable()) //Form 로그인 방식 disable
                .httpBasic((auth) -> auth.disable()) //http basic 인증 방식 disable
                .authorizeHttpRequests((auth) -> auth //경로별 인가 작업
                        .requestMatchers("/login", "/", "/join").permitAll() //로그인이나 회원가입은 인가 x
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()) //다른 요청에 대해서는 로그인이 되어야만 가능
                .sessionManagement((session) -> session //세션 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //JWT에서는 Session을 무상태성으로 관리

        //admin은 ADMIN만 가능

        return http.build();
    }
}