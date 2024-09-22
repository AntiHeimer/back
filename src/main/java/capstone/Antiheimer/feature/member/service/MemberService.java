package capstone.Antiheimer.feature.member.service;

import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.util.CheckService;
import capstone.Antiheimer.util.encrypt.BcryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BcryptService bcryptService;
    private final CheckService checkService;

    /**
     * 회원가입
     * - 공백 확인
     * - 유효성 확인
     * - 중복 확인
     * @param reqDto
     */
    @Transactional
    public void signup(SignupReqDto reqDto) {

        log.info("[Service] 회원 정보 공백 확인");
        checkService.checkMemberNotNull(reqDto);

        log.info("[Service] 회원 정보 유효성 확인");
        checkService.checkMemberValid(reqDto);

        log.info("[Service] 아이디 중복 확인");
        checkService.checkDuplicateId(reqDto);

        Member member = convertToEntity(reqDto);

        log.info("[Service] 회원 정보 저장");
        memberRepository.save(member);
    }

    /**
     * 로그인
     * - 회원 존재 확인
     * - 비밀번호 확인
     * @param reqDto
     * @return
     */
    public String login(LoginReqDto reqDto) {

        log.info("[Service] 아이디 존재 확인");
        checkService.checkIdExists(reqDto.getId());

        log.info("[Service] 비밀번호 일치 확인");
        checkService.checkPwMatches(reqDto);

        return memberRepository.findOneById(reqDto.getId()).getUuid();
    }

    /**
     * 로그아웃
     * - 공백 확인
     * - 유효성 확인
     * - 존재 확인
     * @param memberUuid
     */
    public void logout(String memberUuid) {

        log.info("[Service] UUID 공백 확인");
        checkService.checkUuidNotNull(memberUuid);

        log.info("[Service] UUID 유효성 확인");
        checkService.checkUuidValid(memberUuid);

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);
    }

    public Member convertToEntity(SignupReqDto reqDto) {

        Member member = new Member();

        member.setUuid(UUID.randomUUID().toString());
        member.setId(reqDto.getId());
        member.setName(reqDto.getName());
        member.setBirth(reqDto.getBirth());
        member.setGender(reqDto.getGender());

        String pw = reqDto.getPw();
        member.setPw(bcryptService.encode(pw)); // bcrypt 암호화

        return member;
    }
}
