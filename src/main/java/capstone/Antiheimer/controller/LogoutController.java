package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.LogoutReqDto;
import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.exception.FailLogoutException;
import capstone.Antiheimer.exception.InvalidUuidException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NullUuidException;
import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogoutController {

    @Autowired
    private final LogoutService logoutService;
    @Autowired
    private final AesService aesService;

    @Value("${auth.key}")
    private String authKey;

    @PostMapping("/logout/{uuid}")
    public NormalResDto logout(@PathVariable("uuid") String uuid) {

        NormalResDto result;

        String decryptedUuid = aesService.decryptAES(uuid);

        try {
            log.info("로그아웃 시작");
            logoutService.logout(decryptedUuid);

            log.info("로그아웃 성공");
            result = new NormalResDto("200", "로그아웃 성공");
            return result;
        } catch (NullUuidException e) {
            result = new NormalResDto("405", "입력되지 않은 uuid");
            return result;
        } catch (InvalidUuidException e) {
            result = new NormalResDto("406", "유효하지 않은 uuid");
            return result;
        } catch (NotExistException e) {
            result = new NormalResDto("408", "존재하지 않는 회원");
            return result;
        }
    }
}
