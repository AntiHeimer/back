package capstone.Antiheimer.feature.member.service;

import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.util.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CheckService checkService;

    /**
     * 회원가입
     * - 유효한 형식 확인
     * - 중복 확인
     * - null 확인
     * @param memberDto
     */
    @Transactional
    public void signup(SignupReqDto memberDto) {

        checkService.checkMemberNotNull(memberDto); // null 확인
        checkService.checkMemberValid(memberDto); // 형식 확인
        checkService.checkDuplicateId(memberDto); // 중복 확인

        memberRepository.save(memberDto);
    }

    /**
     * 로그인
     * - 회원 존재 확인
     * - 비밀번호 확인
     *
     * @param reqDto
     * @return
     */
    public String login(LoginReqDto reqDto) {

        String uuid;

        checkService.checkIdExists(reqDto.getId());
        checkService.checkPwMatches(reqDto);

        uuid = memberRepository.findOneById(reqDto.getId()).getUuid();

        return uuid;
    }

    /**
     * 로그아웃
     * - null 확인
     * - 유효한 형식 확인
     * - 존재 확인
     *
     * @param memberUuid
     */
    public void logout(String memberUuid) {

        checkService.checkUuidNotNull(memberUuid);
        checkService.checkUuidValid(memberUuid);
        checkService.checkMemberExists(memberUuid);
    }
}
