package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.SaveSleepReqDto;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SleepDataService {

    private final MemberRepository memberRepository;
    private final SleepDataRepository sleepDataRepository;

    public void insertSleep(SaveSleepReqDto request) {

        memberExistCheck(request.getMemberUuid());

    }

    private void memberExistCheck(String uuid) {

        Member findMember = memberRepository.findOneByUuid(uuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다.");
            throw new NotExistException();
        }
    }
}
