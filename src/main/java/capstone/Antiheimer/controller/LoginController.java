package capstone.Antiheimer.controller;

import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class LoginController {

    @Autowired
    private final LoginService loginService;
    @Autowired
    private final AesService aesService;
    @Autowired
    private final ObjectMapper objectMapper;

    @Value("${auth.key}")
    private String authKey;

    LoginResDto result;

    @PostMapping("/user/login")
    public LoginResDto Login(@RequestHeader("auth") String auth,
                             @RequestBody String request) {

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다");
            result = new LoginResDto("400", "권한 없음", null, null);
            return result;
        }

        try {
            log.info("로그인 시작");
            String decryptedRequest = aesService.decryptAES(request);
            LoginReqDto reqDto = objectMapper.readValue(decryptedRequest, LoginReqDto.class);

            String uuid = loginService.login(reqDto);
            Boolean isParent = loginService.isParent(reqDto);

            log.info("로그인 성공");
            result = new LoginResDto("200", "로그인 성공", uuid, isParent);
            return result;
        } catch (NullIdException e){

            result = new LoginResDto("401", "입력되지 않은 아이디", null, null);
            return result;
        } catch (NullPwException e){

            result = new LoginResDto("401", "입력되지 않은 비밀번호", null, null);
            return result;
        } catch (NotExistException e){

            result = new LoginResDto("404", "존재하지 않는 아이디", null, null);
            return result;
        } catch (IncorrectPwException e){

            result = new LoginResDto("405", "일치하지 않는 비밀번호", null, null);
            return result;
        } catch (LoginStatusException e){

            result = new LoginResDto("406", "이미 로그인된 계정", null, null);
            return result;
        }  catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
