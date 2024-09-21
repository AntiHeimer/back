package capstone.Antiheimer.feature.member.controller;

import capstone.Antiheimer.feature.member.dto.InfoResDto;
import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.feature.member.dto.LoginResDto;
import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.member.service.MemberService;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.jwt.JwtTokenUtil;
import capstone.Antiheimer.util.encrypt.AesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AesService aesService;
    private final ObjectMapper objectMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${auth.key}")
    private String authKey;

    /**
     * 회원가입
     * @param auth
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/signup")
    public ResponseEntity<NormalResDto> signup(@RequestHeader("auth") String auth,
                                              @RequestBody String request) throws JsonProcessingException {

        log.info("[Controller] 권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다");
            return new ResponseEntity<>(new NormalResDto("401", "권한 없음"), HttpStatus.UNAUTHORIZED);
        }

        log.info("[Controller] AES 복호화");
        String decryptedRequest = aesService.decryptAES(request);
        SignupReqDto reqDto = objectMapper.readValue(decryptedRequest, SignupReqDto.class);

        log.info("[Controller] 회원가입 시작");
        memberService.signup(reqDto);

        log.info("[Controller] 회원가입 성공");
        return new ResponseEntity<>(new NormalResDto("200", "회원가입 성공"), HttpStatus.OK);
    }

    /**
     * 로그인
     * @param auth
     * @param request
     * @return LoginResDto
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestHeader("auth") String auth,
                             @RequestBody String request) throws JsonProcessingException {

        log.info("[Controller] 권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다");
            return new ResponseEntity<>(new LoginResDto("401", "권한 없음", null, null), HttpStatus.UNAUTHORIZED);
        }

        log.info("[Controller] AES 복호화");
        String decryptedRequest = aesService.decryptAES(request);
        LoginReqDto reqDto = objectMapper.readValue(decryptedRequest, LoginReqDto.class);

        log.info("[Controller] 로그인 시작");
        String memberUuid = memberService.login(reqDto);

        log.info("[Controller] Jwt 토큰 발급");
        String jwtToken = jwtTokenUtil.generateToken(memberUuid);

        log.info("[Controller] 로그인 성공");
        return new ResponseEntity<>(new LoginResDto("200", "로그인 성공", memberUuid, jwtToken), HttpStatus.OK);
    }

    /**
     * 로그아웃
     * @param memberUuid
     * @return NormalResDto
     */
    @PostMapping("/logout/{uuid}")
    public ResponseEntity<NormalResDto> logout(@PathVariable("uuid") String memberUuid) {

        String decryptedUuid = aesService.decryptAES(memberUuid);

        log.info("[Controller] 로그아웃 시작");
        memberService.logout(decryptedUuid);

        log.info("[Controller] 로그아웃 성공");
        return new ResponseEntity<>(new NormalResDto("200", "로그아웃 성공"), HttpStatus.OK);
    }

    /**
     * 회원정보
     * @param memberUuid
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<InfoResDto> memberInfo(@RequestParam("memberUuid") String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 회원 조회 시작");
        Member member = memberRepository.findOneByUuid(decryptedUuid);

        log.info("[Controller] 회원 조회 성공");
        return new ResponseEntity<>(new InfoResDto("200", "회원 조회 성공", member.getId(), member.getName(), member.getGender(), member.getBirth()), HttpStatus.OK);
    }
}
