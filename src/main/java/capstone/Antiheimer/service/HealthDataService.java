package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.*;
import capstone.Antiheimer.exception.DuplicateHealthDataException;
import capstone.Antiheimer.exception.InvalidDataTypeException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.repository.HealthDataRepository;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HealthDataService {

    private final MemberRepository memberRepository;
    private final HealthDataRepository healthDataRepository;

    /**
     * 활동 데이터 저장
     * - 회원 존재 확인
     * - 활동 데이터 존재 확인(중복 확인)
     * @param request
     */
    @Transactional
    public void insertActive(SaveActiveReqDto request) {

        memberExistCheck(request.getMemberUuid());
        activeDataDuplicateCheck(request);
        healthDataRepository.saveActive(request);
        log.info("Active 데이터 저장 성공");
    }

    /**
     * 움직인 거리 데이터 저장
     * - 회원 존재 확인
     * - 움직인 거리 데이터 존재 확인(중복 확인)
     * @param request
     */
    @Transactional
    public void insertMove(SaveMoveReqDto request) {

        memberExistCheck(request.getMemberUuid());
        moveDataDuplicateCheck(request);
        healthDataRepository.saveMove(request);
        log.info("Move 데이터 저장 성공");
    }

    /**
     * 걸음수 데이터 저장
     * - 회원 존재 확인
     * - 걸음수 데이터 존재 확인(중복 확인)
     * @param request
     */
    @Transactional
    public void insertWalk(SaveWalkReqDto request) {

        memberExistCheck(request.getMemberUuid());
        walkDataDuplicateCheck(request);
        healthDataRepository.saveWalk(request);
        log.info("Walk 데이터 저장 성공");
    }

    /**
     * 몸무게 저장
     * - 회원 존재 확인
     * @param request
     */
    @Transactional
    public void insertWeight(SaveWeightReqDto request) {

        memberExistCheck(request.getMemberUuid());
        healthDataRepository.saveWeight(request);
        log.info("Weight 저장 성공");
    }

    /**
     * 수면 데이터 저장
     * - 회원 존재 확인
     * - 수면 데이터 존재 확인(중복 확인)
     * @param request
     */
    public void insertSleep(SaveSleepReqDto request) {

        memberExistCheck(request.getMemberUuid());
        sleepDataDuplicateCheck(request);
        healthDataRepository.saveSleep(request);
        log.info("수면 데이터 저장 성공");
    }

    /**
     * 건강 데이터 최근 저장 날짜 조회
     * @param uuid
     * @param data
     * @return
     */
    public LocalDate recentDateOfHealthData(String uuid, String data) {

        memberExistCheck(uuid);
        dataTypeExistCheck(data);

        return switch (data) {
            case "active" -> healthDataRepository.findLastSentDateOfActive(uuid);
            case "move" -> healthDataRepository.findLastSentDateOfMove(uuid);
            case "walk" -> healthDataRepository.findLastSentDateOfWalk(uuid);
            case "sleep" -> healthDataRepository.findLastSentDateOfSleep(uuid);
            default -> null;
        };
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
//        walkDataDuplicateCheck(walkList);
//
//        log.info("Walk 데이터 조회 성공");
//
//        return walkList;
//    }
//

    /**
     * 회원 존재 확인
     * @param uuid
     */
    private void memberExistCheck(String uuid) {

        Member findMember = memberRepository.findOneByUuid(uuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistException();
        }
    }

    /**
     * 활동 데이터 존재 확인
     * @param request
     */
    private void activeDataDuplicateCheck(SaveActiveReqDto request) {


        if (healthDataRepository.existActive(request)) {

            log.warn("이미 존재하는 활동 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 움직인 거리 데이터 존재 확인
     * @param request
     */
    private void moveDataDuplicateCheck(SaveMoveReqDto request) {

        if (healthDataRepository.existMove(request)) {

            log.warn("이미 존재하는 움직인 거리 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 걸음수 데이터 존재 확인
     * @param request
     */
    private void walkDataDuplicateCheck(SaveWalkReqDto request) {

        if (healthDataRepository.existWalk(request)) {

            log.warn("이미 존재하는 걸음수 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 데이터 타입 존재 확인
     */
    private void dataTypeExistCheck(String data) {

        if (!data.equals("active") &&  !data.equals("move") && !data.equals("walk") && !data.equals("sleep")) {

            throw new InvalidDataTypeException();
        }
    }

    /**
     * 수면 데이터 존재 확인
     * @param request
     */
    private void sleepDataDuplicateCheck(SaveSleepReqDto request) {

        if (healthDataRepository.existSleep(request)) {

            log.warn("이미 존재하는 수면 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

}
