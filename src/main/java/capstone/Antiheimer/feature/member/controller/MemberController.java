package capstone.Antiheimer.feature.member.controller;

import capstone.Antiheimer.exception.duplicate.DuplicateIdException;
import capstone.Antiheimer.exception.invalid.*;
import capstone.Antiheimer.exception.nullE.*;
import capstone.Antiheimer.feature.member.dto.InfoResDto;
import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.feature.member.dto.LoginResDto;
import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.member.service.LoginService;
import capstone.Antiheimer.feature.member.service.LogoutService;
import capstone.Antiheimer.feature.member.service.SignUpService;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.jwt.JwtTokenUtil;
import capstone.Antiheimer.util.encrypt.AesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    @Autowired
    private final SignUpService signUpService;
    @Autowired
    private final LoginService loginService;
    @Autowired
    private final LogoutService logoutService;
    @Autowired
    private final AesService aesService;
    @Autowired
    private final ObjectMapper objectMapper;
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${auth.key}")
    private String authKey;

    /**
     * 회원가입
     *
     * @param auth
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/signup")
    public NormalResDto signup(@RequestHeader("auth") String auth,
                               @RequestBody String request) {

        NormalResDto result;

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다.");
            result = new NormalResDto("401", "권한 없음");
            return result;
        }

        log.info("회원가입 시작");
        try {
            String decryptedRequest = aesService.decryptAES(request);
            SignupReqDto reqDto = objectMapper.readValue(decryptedRequest, SignupReqDto.class);
            signUpService.signup(reqDto);

            log.info("보호자 회원가입 성공");
            result = new NormalResDto("200", "회원 회원가입 성공");
            return result;
        } catch (NullIdException e) {

            result = new NormalResDto("405", "입력되지 않은 아이디");
            return result;
        } catch (NullNameException e) {

            result = new NormalResDto("405", "입력되지 않은 이름");
            return result;
        } catch (NullPwException e) {

            result = new NormalResDto("405", "입력되지 않은 비밀번호");
            return result;
        } catch (NullGenderException e) {

            result = new NormalResDto("405", "입력되지 않은 성별");
            return result;
        } catch (NullBirthException e) {

            result = new NormalResDto("405", "입력되지 않은 생일");
            return result;
        } catch (InvalidIdException e) {

            result = new NormalResDto("406", "유효하지 않은 아이디");
            return result;
        } catch (InvalidPwException e) {

            result = new NormalResDto("406", "유효하지 않은 비밀번호");
            return result;
        } catch (InvalidNameException e) {

            result = new NormalResDto("406", "유효하지 않은 이름");
            return result;
        } catch (InvalidGenderException e) {

            result = new NormalResDto("406", "유효하지 않은 성별");
            return result;
        } catch (DuplicateIdException e) {

            result = new NormalResDto("407", "중복된 아이디");
            return result;
        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 로그인
     *
     * @param auth
     * @param request
     * @return LoginResDto
     */
    @PostMapping("/login")
    public LoginResDto login(@RequestHeader("auth") String auth,
                             @RequestBody String request) throws JsonProcessingException {

        LoginResDto result;

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다.");
            result = new LoginResDto("401", "권한 없음", null, null);
            return result;
        }

        log.info("로그인 시작");
        String decryptedRequest = aesService.decryptAES(request);
        LoginReqDto reqDto = objectMapper.readValue(decryptedRequest, LoginReqDto.class);

        String uuid = loginService.login(reqDto);

        String jwtToken = jwtTokenUtil.generateToken(uuid);
        log.info("Jwt 토큰 발급 성공");

        return new LoginResDto("200", "로그인 성공", uuid, jwtToken);
    }

    /**
     * 로그아웃
     *
     * @param uuid
     * @return NormalResDto
     */
    @PostMapping("/logout/{uuid}")
    public NormalResDto logout(@PathVariable("uuid") String uuid) {

        String decryptedUuid = aesService.decryptAES(uuid);

        log.info("로그아웃 시작");
        logoutService.logout(decryptedUuid);

        log.info("로그아웃 성공");
        return new NormalResDto("200", "로그아웃 성공");

    }

    /**
     * 회원정보
     *
     * @param memberUuid
     * @return
     */
    @GetMapping("/info")
    public InfoResDto memberInfo(@RequestParam("memberUuid") String memberUuid) throws UnsupportedEncodingException {

        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());

        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");

        // uuid 복호화
        String decryptedUuid = aesService.decryptAES(plusEncodedString);

        Member member = memberRepository.findOneByUuid(decryptedUuid);

        return new InfoResDto("200", "회원 조회 성공", member.getId(), member.getName(), member.getGender(), member.getBirth());
    }
}
