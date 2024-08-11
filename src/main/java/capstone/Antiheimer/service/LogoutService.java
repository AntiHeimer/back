package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.LogoutReqDto;
import capstone.Antiheimer.exception.FailLogoutException;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LogoutService {

    private final MemberRepository memberRepository;

    public void logout(LogoutReqDto request) {

        String uuid = request.getUuid();

        Member member = memberRepository.findOneByUuid(uuid);

        if (member == null) {
            log.info("로그아웃 실패");
            throw new FailLogoutException();
        }
    }
}
