package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Active;
import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.domain.Move;
import capstone.Antiheimer.domain.Walk;
import capstone.Antiheimer.dto.*;
import capstone.Antiheimer.exception.DuplicateHealthDataException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.repository.HealthDataRepository;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        activeDataExistCheck(request);
        healthDataRepository.saveActive(request);
        log.info("Active 데이터 저장 성공");
    }

    @Transactional
    public void insertMove(SaveMoveReqDto request) {

        memberExistCheck(request.getMemberUuid());
        moveDataExistCheck(request);
        healthDataRepository.saveMove(request);
        log.info("Move 데이터 저장 성공");
    }

    @Transactional
    public void insertWalk(SaveWalkReqDto request) {

        memberExistCheck(request.getMemberUuid());
        walkDataExistCheck(request);
        healthDataRepository.saveWalk(request);
        log.info("Walk 데이터 저장 성공");
    }

    @Transactional
    public void insertWeight(SaveWeightReqDto request) {

        memberExistCheck(request.getMemberUuid());
        healthDataRepository.saveWeight(request);
        log.info("Weight 저장 성공");
    }
//    public List<Active> findActiveList(String uuid, LocalDate date) {
//
//        memberExistCheck(uuid);
//        List<Active> activeList = healthDataRepository.findActive(uuid, date);
//        activeDataExistCheck(activeList);
//
//        log.info("Active 데이터 조회 성공");
//
//        return activeList;
//    }
//
//    public List<Move> findMoveList(String uuid, LocalDate date) {
//
//        memberExistCheck(uuid);
//        List<Move> moveList = healthDataRepository.findMove(uuid, date);
//        moveDataExistCheck(moveList);
//
//        log.info("Move 데이터 조회 성공");
//
//        return moveList;
//    }
//
//    public List<Walk> findWalkList(String uuid, LocalDate date) {
//
//        memberExistCheck(uuid);
//        List<Walk> walkList = healthDataRepository.findWalk(uuid, date);
//        walkDataExistCheck(walkList);
//
//        log.info("Walk 데이터 조회 성공");
//
//        return walkList;
//    }
//
    private void memberExistCheck(String uuid) {

        Member findMember = memberRepository.findOneByUuid(uuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistException();
        }
    }

    private void activeDataExistCheck(SaveActiveReqDto request) {


        if (healthDataRepository.existActive(request)) {

            log.warn("이미 존재하는 활동 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    private void moveDataExistCheck(SaveMoveReqDto request) {

        if (healthDataRepository.existMove(request)) {

            log.warn("이미 존재하는 움직인 거리 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    private void walkDataExistCheck(SaveWalkReqDto request) {

        if (healthDataRepository.existWalk(request)) {

            log.warn("이미 존재하는 걸음수 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }
}
