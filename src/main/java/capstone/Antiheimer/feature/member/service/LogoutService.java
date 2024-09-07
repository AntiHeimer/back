package capstone.Antiheimer.feature.member.service;

import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.exception.InvalidUuidException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NullUuidException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.StringUtils.containsWhitespace;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LogoutService {

    private final MemberRepository memberRepository;

    /**
     * 로그아웃
     * - null 확인
     * - 유효한 형식 확인
     * - 존재 확인
     *
     * @param uuid
     */
    public void logout(String uuid) {

        nullUuid(uuid);
        validateUuid(uuid);
        existUuid(uuid);
        log.info("로그아웃 성공");
    }

    public void nullUuid(String uuid) {

        Member member = memberRepository.findOneByUuid(uuid);

        if (member.getUuid().isEmpty()) {

            log.warn("uuid가 비어있습니다");
            throw new NullUuidException();
        }
    }

    public void validateUuid(String uuid) {

        Member member = memberRepository.findOneByUuid(uuid);

        if (containsWhitespace(member.getUuid()) || member.getUuid().length() != 36) {

            log.warn("유효하지 않은 uuid입니다");
            throw new InvalidUuidException();
        }
    }

    public void existUuid(String uuid) {

        Member member = memberRepository.findOneByUuid(uuid);

        if (memberRepository.findOneByUuid(member.getUuid()) == null) {

            log.warn("회원이 존재하지 않습니다");
            throw new NotExistException();
        }
    }
}
