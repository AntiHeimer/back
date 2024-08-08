package capstone.Antiheimer.controller;

import capstone.Antiheimer.Jwt.JwtTokenUtil;
import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.InfoResDto;
import capstone.Antiheimer.dto.LoginReqDto;
import capstone.Antiheimer.dto.LoginResDto;
import capstone.Antiheimer.exception.IncorrectPwException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NullIdException;
import capstone.Antiheimer.exception.NullPwException;
import capstone.Antiheimer.repository.MemberRepository;
import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final AesService aesService;
    private final ObjectMapper objectMapper;
    private final JwtTokenUtil jwtTokenUtil;

    LoginResDto result;

    @PostMapping("/login")
    public LoginResDto login(@RequestBody LoginReqDto reqDto) {

        try {
            log.info("로그인 시작");
//            String decryptedRequest = aesService.decryptAES(request);
//            LoginReqDto reqDto = objectMapper.readValue(decryptedRequest, LoginReqDto.class);

            String uuid = loginService.login(reqDto);
            log.info("로그인 성공");

            String jwtToken = jwtTokenUtil.generateToken(uuid);
            log.info("Jwt 토큰 발급 성공");

            result = new LoginResDto("200", "로그인 성공", uuid, jwtToken);
            return result;
        } catch (NullIdException e) {

            result = new LoginResDto("401", "입력되지 않은 아이디", null, null);
            return result;
        } catch (NullPwException e) {

            result = new LoginResDto("401", "입력되지 않은 비밀번호", null, null);
            return result;
        } catch (NotExistException e) {

            result = new LoginResDto("404", "존재하지 않는 아이디", null, null);
            return result;
        } catch (IncorrectPwException e) {

            result = new LoginResDto("405", "일치하지 않는 비밀번호", null, null);
            return result;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
        }
    }

    @GetMapping("/info/{uuid}")
    public InfoResDto userInfo(@PathVariable("uuid") String uuid) {

        Member member = memberRepository.findOneByUuid(uuid);

        return new InfoResDto("200", "회원 조회 성공", member.getUuid(), member.getId(), member.getName());
    }
}
