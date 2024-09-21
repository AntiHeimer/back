package capstone.Antiheimer.feature.health_data.service;

import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.feature.health_data.entity.HealthData;
import capstone.Antiheimer.feature.health_data.repository.HealthDataRepository;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.exception.duplicate.DuplicateHealthDataException;
import capstone.Antiheimer.exception.invalid.InvalidDataTypeException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.util.CheckService;
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
    private final CheckService checkService;

    /**
     * 활동 데이터 저장
     * - 회원 존재 확인
     * - 활동 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertActive(SaveActiveReqDto reqDto) {

        checkService.checkMemberExists(reqDto.getMemberUuid());
        checkService.checkActiveDataDuplicate(reqDto);

        healthDataRepository.saveActive(reqDto);
        log.info("[Service] 활동 데이터 저장 성공");
    }

    /**
     * 움직인 거리 데이터 저장
     * - 회원 존재 확인
     * - 움직인 거리 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertMove(SaveMoveReqDto reqDto) {

        checkService.checkMemberExists(reqDto.getMemberUuid());
        checkService.checkMoveDataDuplicate(reqDto);

        healthDataRepository.saveMove(reqDto);
        log.info("[Service] 움직인 거리 데이터 저장 성공");
    }

    /**
     * 걸음수 데이터 저장
     * - 회원 존재 확인
     * - 걸음수 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertWalk(SaveWalkReqDto reqDto) {

        checkService.checkMemberExists(reqDto.getMemberUuid());
        checkService.checkWalkDataDuplicate(reqDto);

        healthDataRepository.saveWalk(reqDto);
        log.info("[Service] 걸음수 데이터 저장 성공");
    }

    /**
     * 몸무게 저장
     * - 회원 존재 확인
     * @param reqDto
     */
    @Transactional
    public void insertWeight(SaveWeightReqDto reqDto) {

        checkService.checkMemberExists(reqDto.getMemberUuid());

        healthDataRepository.saveWeight(reqDto);
        log.info("[Service] 몸무게 저장 성공");
    }

    /**
     * 수면 데이터 저장
     * - 회원 존재 확인
     * - 수면 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertSleep(SaveSleepReqDto reqDto) {

        checkService.checkMemberExists(reqDto.getMemberUuid());
        checkService.checkSleepDataDuplicate(reqDto);

        healthDataRepository.saveSleep(reqDto);
        log.info("[Service] 수면 데이터 저장 성공");
    }

    /**
     * 건강 데이터 최근 저장 날짜 조회
     * @param memberUuid
     * @param data
     * @return
     */
    public LocalDate recentDateOfHealthData(String memberUuid, String data) {

        checkService.checkMemberExists(memberUuid);
        checkService.checkDataTypeValid(data);

        return switch (data) {
            case "active" -> healthDataRepository.findLastSentDateOfActive(memberUuid);
            case "move" -> healthDataRepository.findLastSentDateOfMove(memberUuid);
            case "walk" -> healthDataRepository.findLastSentDateOfWalk(memberUuid);
            case "sleep" -> healthDataRepository.findLastSentDateOfSleep(memberUuid);
            default -> null;
        };

    }

    /**
     * 건강 데이터 조회
     * @param reqDto
     * @return
     */
    public List<HealthData> findHealthDataByMemberUuid(FindHealthDataReqDto reqDto) {

        List<HealthData> healthDataList = healthDataRepository.findHealthDataByMemberUuid(reqDto.getMemberUuid(), reqDto.getDate());

        checkService.checkMemberExists(reqDto.getMemberUuid());
        checkService.checkHealthDataExists(reqDto);

        log.info("[Service] 건강 데이터 조회 성공");
        return healthDataList;
    }

//    public List<Active> findActiveList(String memberUuid, LocalDate date) {
//
//        checkIdExists(memberUuid);
//        List<Active> activeList = healthDataRepository.findActive(memberUuid, date);
//        activeDataExistCheck(activeList);
//
//        log.info("[Controller] Active 데이터 조회 성공");
//
//        return activeList;
//    }
//
//    public List<Move> findMoveList(String memberUuid, LocalDate date) {
//
//        checkIdExists(memberUuid);
//        List<Move> moveList = healthDataRepository.findMove(memberUuid, date);
//        moveDataExistCheck(moveList);
//
//        log.info("[Controller] Move 데이터 조회 성공");
//
//        return moveList;
//    }
//
//    public List<Walk> findWalkList(String memberUuid, LocalDate date) {
//
//        checkIdExists(memberUuid);
//        List<Walk> walkList = healthDataRepository.findWalk(memberUuid, date);
//        walkDataDuplicateCheck(walkList);
//
//        log.info("[Controller] Walk 데이터 조회 성공");
//
//        return walkList;
//    }
}
