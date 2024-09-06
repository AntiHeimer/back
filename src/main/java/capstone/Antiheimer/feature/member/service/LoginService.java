package capstone.Antiheimer.feature.member.service;

import capstone.Antiheimer.feature.member.Member;
import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.exception.IncorrectPwException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.member.MemberRepository;
import capstone.Antiheimer.util.encrypt.BcryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final BcryptService bcryptService;

    /**
     * 로그인
     * - 회원 존재 확인
     * - 비밀번호 확인
     * @param request
     * @return
     */
    public String login(LoginReqDto request) {

        String uuid;

        memberExistCheck(request.getId());
        memberCorrectPw(request);

        uuid = memberRepository.findOneById(request.getId()).getUuid();

        log.info("로그인 성공");
        return uuid;
    }

    /**
     * 회원 존재 확인
     * @param id
     */
    private void memberExistCheck(String id) {

        List<Member> findMember = memberRepository.findById(id);

        if (findMember.isEmpty()) {

            log.warn("존재하지 않는 아이디입니다");
            throw new NotExistException();
        }
    }

    /**
     * 비밀번호 확인
     * @param request
     */
    private void memberCorrectPw(LoginReqDto request) {

        Member findMember = memberRepository.findOneById(request.getId());

        if (!bcryptService.isPwMatch(request.getPw(), findMember.getPw())) {

            log.warn("비밀번호가 일치하지 않습니다");
            throw new IncorrectPwException();
        }
    }
}
