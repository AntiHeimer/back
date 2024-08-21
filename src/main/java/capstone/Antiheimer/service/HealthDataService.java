package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Active;
import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.domain.Move;
import capstone.Antiheimer.domain.Walk;
import capstone.Antiheimer.dto.*;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NotExistHealthDataException;
import capstone.Antiheimer.repository.HealthDataRepository;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HealthDataService {

    private final MemberRepository memberRepository;
    private final HealthDataRepository healthDataRepository;

    @Transactional
    public void insertActive(SaveActiveReqDto request) {

        memberExistCheck(request.getMemberUuid());
        healthDataRepository.saveActive(request);
        log.info("Active 데이터 저장 성공");
    }

    @Transactional
    public void insertMove(SaveMoveReqDto request) {

        memberExistCheck(request.getMemberUuid());
        healthDataRepository.saveMove(request);
        log.info("Move 데이터 저장 성공");
    }

    @Transactional
    public void insertWalk(SaveWalkReqDto request) {

        memberExistCheck(request.getMemberUuid());
        healthDataRepository.saveWalk(request);
        log.info("Walk 데이터 저장 성공");
    }

    public List<Active> findActiveList(String uuid, LocalDate date) {

        memberExistCheck(uuid);
        List<Active> activeList = healthDataRepository.findActive(uuid, date);
        activeDataExistCheck(activeList);

        log.info("Active 데이터 조회 성공");

        return activeList;
    }

    public List<Move> findMoveList(String uuid, LocalDate date) {

        memberExistCheck(uuid);
        List<Move> moveList = healthDataRepository.findMove(uuid, date);
        moveDataExistCheck(moveList);

        log.info("Move 데이터 조회 성공");

        return moveList;
    }

    public List<Walk> findWalkList(String uuid, LocalDate date) {

        memberExistCheck(uuid);
        List<Walk> walkList = healthDataRepository.findWalk(uuid, date);
        walkDataExistCheck(walkList);

        log.info("Walk 데이터 조회 성공");

        return walkList;
    }

    private void memberExistCheck(String uuid) {

        Member findMember = memberRepository.findOneByUuid(uuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistException();
        }
    }

    private void activeDataExistCheck(List<Active> activeList) {

        if (activeList.isEmpty()) {

            log.warn("존재하지 않는 활동 데이터입니다");
            throw new NotExistHealthDataException();
        }
    }

    private void moveDataExistCheck(List<Move> moveList) {

        if (moveList.isEmpty()) {

            log.warn("존재하지 않는 움직인 거리 데이터입니다");
            throw new NotExistHealthDataException();
        }
    }

    private void walkDataExistCheck(List<Walk> walkList) {

        if (walkList.isEmpty()) {

            log.warn("존재하지 않는 걸음수 데이터입니다");
            throw new NotExistHealthDataException();
        }
    }
}
