package capstone.Antiheimer.controller;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.*;
import capstone.Antiheimer.exception.*;
import capstone.Antiheimer.firebase.FcmService;
import capstone.Antiheimer.jwt.JwtTokenUtil;
import capstone.Antiheimer.repository.MemberRepository;
import capstone.Antiheimer.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final SignUpService signUpService;
    @Autowired
    private final LoginService loginService;
    @Autowired
    private final LogoutService logoutService;
    @Autowired
    private final FcmService fcmService;
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
                             @RequestBody String request) {

        LoginResDto result;

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다.");
            result = new LoginResDto("401", "권한 없음", null, null);
            return result;
        }

        log.info("로그인 시작");
        try {
            String decryptedRequest = aesService.decryptAES(request);
            LoginReqDto reqDto = objectMapper.readValue(decryptedRequest, LoginReqDto.class);

            String uuid = loginService.login(reqDto);

            String jwtToken = jwtTokenUtil.generateToken(uuid);
            log.info("Jwt 토큰 발급 성공");

            result = new LoginResDto("200", "로그인 성공", uuid, jwtToken);
            return result;
        } catch (NullIdException e) {

            result = new LoginResDto("405", "입력되지 않은 아이디", null, null);
            return result;
        } catch (NullPwException e) {

            result = new LoginResDto("405", "입력되지 않은 비밀번호", null, null);
            return result;
        } catch (NotExistException e) {

            result = new LoginResDto("408", "존재하지 않는 아이디", null, null);
            return result;
        } catch (IncorrectPwException e) {

            result = new LoginResDto("409", "일치하지 않는 비밀번호", null, null);
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * 회원정보
     *
     * @param uuid
     * @return InfoResDto
     */
    @GetMapping("/info/{uuid}")
    public InfoResDto userInfo(@PathVariable("uuid") String uuid) {

        String decryptedUuid = aesService.decryptAES(uuid);
        Member member = memberRepository.findOneByUuid(decryptedUuid);

        return new InfoResDto("200", "회원 조회 성공", member.getUuid(), member.getId(), member.getName());
    }

    @PostMapping("/save/device-token")
    private NormalResDto saveDeviceToken(@RequestBody TokenReqDto request) {

        try {
            log.info("디바이스 토큰 저장 시작");
            fcmService.updateDeviceToken(request);

            log.info("디바이스 토큰 저장 성공");
            return new NormalResDto("200", "디바이스 토큰 저장 성공");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }
}
