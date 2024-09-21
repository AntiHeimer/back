package capstone.Antiheimer.feature.member.service;

import capstone.Antiheimer.exception.duplicate.DuplicateIdException;
import capstone.Antiheimer.exception.duplicate.DuplicateRelationException;
import capstone.Antiheimer.exception.incorrect.IncorrectPwException;
import capstone.Antiheimer.exception.invalid.*;
import capstone.Antiheimer.exception.notexist.NotExistIdException;
import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.exception.nullE.*;
import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.relation.entity.Relation;
import capstone.Antiheimer.feature.relation.repository.RelationRepository;
import capstone.Antiheimer.util.encrypt.BcryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.StringUtils.containsWhitespace;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final BcryptService bcryptService;

    /**
     * uuid 공백 확인
     *
     * @param uuid
     */
    public void checkUuidNotNull(String memberUuid) {

        Member member = memberRepository.findOneByUuid(memberUuid);

        if (member.getUuid().isEmpty()) {

            log.warn("uuid가 비어있습니다");
            throw new NullUuidException();
        }
    }

    /**
     * 회원 정보 공백 확인
     *
     * @param memberDto
     */
    public void checkMemberNotNull(SignupReqDto memberDto){

        if (memberDto.getId().isEmpty()) {

            log.warn("아이디가 비어있습니다");
            throw new NullIdException();
        }
        if (memberDto.getName().isEmpty()) {

            log.warn("이름이 비어있습니다");
            throw new NullNameException();
        }
        if (memberDto.getPw().isEmpty()) {

            log.warn("비밀번호가 비어있습니다");
            throw new NullPwException();
        }
        if (memberDto.getGender().isEmpty()) {

            log.warn("성별이 비어있습니다");
            throw new NullGenderException();
        }
        if (memberDto.getBirth() == null) {

            log.warn("생일이 비어있습니다");
            throw new NullBirthException();
        }
    }

    /**
     * uuid 유효성 확인
     *
     * @param uuid
     */
    public void checkUuidValid(String memberUuid) {

        Member member = memberRepository.findOneByUuid(memberUuid);

        if (containsWhitespace(member.getUuid()) || member.getUuid().length() != 36) {

            log.warn("유효하지 않은 uuid입니다");
            throw new InvalidUuidException();
        }
    }

    /**
     * 회원 정보 유효성 확인
     *
     * @param memberDto
     */
    public void checkMemberValid(SignupReqDto memberDto) {

        if (memberDto.getId().length() < 8 || !memberDto.getId().matches("^[a-zA-Z0-9]+$")) {

            log.warn("유효하지 않은 아이디입니다");
            throw new InvalidIdException();
        }
        if (!memberDto.getPw().matches("^[a-zA-Z0-9]+$")) {

            log.warn("유효하지 않은 비밀번호입니다");
            throw new InvalidPwException();
        }
        if (containsWhitespace(memberDto.getName())) {

            log.warn("유효하지 않은 이름입니다");
            throw new InvalidNameException();
        }
        if (!memberDto.getGender().equals("female") && !memberDto.getGender().equals("male")) {

            log.warn("유효하지 않은 성별입니다");
            throw new InvalidGenderException();
        }
    }

    /**
     * 회원 존재 확인
     *
     * @param uuid
     */
    public void checkMemberExists(String memberUuid) {

        Member member = memberRepository.findOneByUuid(memberUuid);

        if (memberRepository.findOneByUuid(member.getUuid()) == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistMemberException();
        }
    }

    /**
     * 아이디 존재 확인
     *
     * @param id
     */
    public void checkIdExists(String id) {

        List<Member> findMember = memberRepository.findById(id);

        if (findMember.isEmpty()) {

            log.warn("존재하지 않는 아이디입니다");
            throw new NotExistIdException();
        }
    }

    /**
     * 비밀번호 일치 확인
     *
     * @param reqDto
     */
    public void checkPwMatches(LoginReqDto reqDto) {

        Member findMember = memberRepository.findOneById(reqDto.getId());

        if (!bcryptService.isPwMatch(reqDto.getPw(), findMember.getPw())) {

            log.warn("비밀번호가 일치하지 않습니다");
            throw new IncorrectPwException();
        }
    }

    /**
     * 아이디 중복 확인
     *
     * @param memberDto
     */
    public void checkDuplicateId(SignupReqDto memberDto) {

        if (!memberRepository.findById(memberDto.getId()).isEmpty()) {

            log.warn("이미 존재하는 아이디입니다");
            throw new DuplicateIdException();
        }
    }

    /**
     * 관계 중복 확인
     *
     * @param relation
     */
    public void checkDuplicateRelation(Relation relation) {

        if (!relationRepository.isRelationExist(relation)) {

            log.warn("이미 존재하는 관계입니다");
            throw new DuplicateRelationException();
        }
    }
}
