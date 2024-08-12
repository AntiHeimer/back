package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.exception.FailLogoutException;
import capstone.Antiheimer.exception.InvalidUuidException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NullUuidException;
import capstone.Antiheimer.repository.MemberRepository;
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

    public void logout(String uuid) {

        Member member = memberRepository.findOneByUuid(uuid);

        if (member.getUuid().isEmpty()) {
            log.warn("uuid가 비어있습니다");
            throw new NullUuidException();
        }
        if (containsWhitespace(member.getUuid()) || member.getUuid().length() != 36) {
            log.warn("유효하지 않은 uuid입니다");
            throw new InvalidUuidException();
        }
        if (memberRepository.findOneByUuid(member.getUuid()) == null) {
            log.warn("회원이 존재하지 않습니다");
            throw new NotExistException();
        }
    }
}
