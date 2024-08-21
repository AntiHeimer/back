package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NormalResDto;
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

    @PostMapping("/logout/{uuid}")
    public NormalResDto logout(@PathVariable("uuid") String uuid) {

        String decryptedUuid = aesService.decryptAES(uuid);

        try {
            log.info("로그아웃 시작");
            logoutService.logout(decryptedUuid);

            log.info("로그아웃 성공");
            return new NormalResDto("200", "로그아웃 성공");
        } catch (NullUuidException e) {
            return new NormalResDto("405", "입력되지 않은 uuid");
        } catch (InvalidUuidException e) {
            return new NormalResDto("406", "유효하지 않은 uuid");
        } catch (NotExistException e) {
            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }
}
