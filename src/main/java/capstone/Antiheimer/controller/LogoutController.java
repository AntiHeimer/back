package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.LogoutReqDto;
import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.exception.FailLogoutException;
import capstone.Antiheimer.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogoutController {

    @Autowired
    private final LogoutService logoutService;

    @Value("${auth.key}")
    private String authKey;

    @PostMapping("/logout")
    public NormalResDto logout(@RequestHeader("auth") String auth,
                               @RequestBody LogoutReqDto request) {

        NormalResDto result;

        log.info("권환 확인");
        if (!auth.equals(authKey)) {

            log.error("권한이 없습니다");
            result = new NormalResDto("400", "권한 없음");
            return result;
        }

        try {
            log.info("로그아웃 시작");
            logoutService.logout(request);

            log.info("로그아웃 성공");
            result = new NormalResDto("200", "로그아웃 성공");
            return result;
        } catch (FailLogoutException e) {
            result = new NormalResDto("403", "로그아웃 실패");
            return result;
        }
    }
}
