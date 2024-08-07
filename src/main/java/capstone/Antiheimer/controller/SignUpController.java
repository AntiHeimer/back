package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.exception.*;
import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.SignUpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

    @Autowired
    private final SignUpService signUpService;
    @Autowired
    private final AesService aesService;
    @Autowired
    private final ObjectMapper objectMapper;

    @Value("아직몰라")
    private String authKey;

    private NormalResDto result;

    public NormalResDto signUp(@RequestHeader("auth") String auth,
                               @RequestBody String request) {

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다.");
            result = new NormalResDto("401", "권한 없음");
            return result;
        }

        log.info("보호자 회원가입 시작");
        try {

            String decryptedRequest = aesService.decryptAES(request);
            SignupReqDto reqDto = objectMapper.readValue(decryptedRequest, SignupReqDto.class);
            signUpService.signup(reqDto);

            log.info("보호자 회원가입 성공");
            result = new NormalResDto("200", "보호자 회원가입 성공");
            return result;
        } catch (InvalidIdException e) {

            result = new NormalResDto("405", "유효하지 않은 아이디");
            return result;
        } catch (InvalidPwException e) {

            result = new NormalResDto("405", "유효하지 않은 비밀번호");
            return result;
        } catch (InvalidNameException e) {

            result = new NormalResDto("405", "유효하지 않은 이름");
            return result;
        } catch (DuplicateIdException e) {

            result = new NormalResDto("406", "중복된 아이디");
            return result;
        } catch (NullIdException e) {

            result = new NormalResDto("407", "입력되지 않은 아이디");
            return result;
        } catch (NullNameException e) {

            result = new NormalResDto("407", "입력되지 않은 이름");
            return result;
        } catch (NullPwException e) {

            result = new NormalResDto("407", "입력되지 않은 비밀번호");
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
