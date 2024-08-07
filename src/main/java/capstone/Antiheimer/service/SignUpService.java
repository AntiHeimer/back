package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.exception.*;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.springframework.util.StringUtils.containsWhitespace;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SignUpService {

    private final MemberRepository memberRepository;
    private final AesService aesService;

    @Transactional
    public void signup(SignupReqDto memberDto) {

        validateMember(memberDto); // 형식 확인
        duplicateMember(memberDto); // 중복 확인
        nullMember(memberDto); // null 확인
        memberRepository.save(memberDto);
        log.info("회원가입 성공");
    }

    private void validateMember(SignupReqDto memberDto) {

        if (memberDto.getId().length() <8 || !memberDto.getId().matches("^[a-zA-Z0-9]+$")) {
            log.warn("유효하지 않은 아이디입니다.");
            throw new InvalidIdException();
        }
        if (!memberDto.getPw().matches("^[a-zA-Z0-9]+$")) {
            log.warn("유효하지 않은 비밀번호입니다.");
            throw new InvalidPwException();
        }
        if (containsWhitespace(memberDto.getName())) {
            log.warn("유효하지 않은 이름입니다.");
            throw new InvalidNameException();
        }
    }

    private void duplicateMember(SignupReqDto memberDto) {

        if (!memberRepository.findById(memberDto.getId()).isEmpty()) {
            log.warn("이미 존재하는 아이디입니다.");
            throw new DuplicateIdException();
        }
        List<Member> findMembersId = memberRepository.findById(memberDto.getId());
        if (!findMembersId.isEmpty()) {
            log.warn("이미 존재하는 회원입니다.");
            throw new DuplicateIdException();
        }
    }

    private void nullMember(SignupReqDto memberDto){

        if (memberDto.getId().isEmpty()) {
            log.warn("아이디가 비어있습니다.");
            throw new NullIdException();
        }
        if (memberDto.getName().isEmpty()) {
            log.warn("이름이 비어있습니다.");
            throw new NullNameException();
        }
        if (memberDto.getPw().isEmpty()) {
            log.warn("비밀번호가 비어있습니다.");
            throw new NullPwException();
        }
    }
}
