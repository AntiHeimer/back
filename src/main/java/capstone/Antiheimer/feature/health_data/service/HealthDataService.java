package capstone.Antiheimer.feature.health_data.service;

import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.feature.health_data.entity.*;
import capstone.Antiheimer.feature.health_data.repository.HealthDataRepository;
import capstone.Antiheimer.util.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HealthDataService {

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

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 활동 데이터 중복 확인");
        checkService.checkActiveDataDuplicate(reqDto);

        log.info("[Service] 활동 데이터 저장");
        healthDataRepository.saveActive(reqDto);
    }

    /**
     * 움직인 거리 데이터 저장
     * - 회원 존재 확인
     * - 움직인 거리 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertMove(SaveMoveReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 움직인 거리 데이터 중복 확인");
        checkService.checkMoveDataDuplicate(reqDto);

        log.info("[Service] 움직인 거리 데이터 저장");
        healthDataRepository.saveMove(reqDto);
    }

    /**
     * 걸음수 데이터 저장
     * - 회원 존재 확인
     * - 걸음수 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertWalk(SaveWalkReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 걸음수 거리 데이터 중복 확인");
        checkService.checkWalkDataDuplicate(reqDto);

        log.info("[Service] 걸음수 데이터 저장");
        healthDataRepository.saveWalk(reqDto);
    }

    /**
     * 수면 데이터 저장
     * - 회원 존재 확인
     * - 수면 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertSleep(SaveSleepReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 수면 데이터 중복 확인");
        checkService.checkSleepDataDuplicate(reqDto);

        log.info("[Service] 수면 데이터 저장");
        healthDataRepository.saveSleep(reqDto);
    }

    /**
     * 몸무게 데이터 저장
     * - 회원 존재 확인
     * @param reqDto
     */
    @Transactional
    public void insertWeight(SaveWeightReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 몸무게 데이터 저장");
        healthDataRepository.saveWeight(reqDto);
    }

    /**
     * 건강 데이터 최근 저장 날짜 조회
     * @param memberUuid
     * @param data
     * @return
     */
    public LocalDate recentDateOfHealthData(String memberUuid, String data) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 데이터 타입 유효성 확인");
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

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 건강 데이터 존재 확인");
        checkService.checkHealthDataExists(reqDto);

        log.info("[Service] 건강 데이터 조회");
        return healthDataRepository.findHealthDataByMemberUuid(reqDto.getMemberUuid(), reqDto.getDate());
    }

    /**
     * 활동 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<Active> findActiveList(String memberUuid, LocalDate date) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkIdExists(memberUuid);

        log.info("[Controller] 활동 데이터 조회");
        return healthDataRepository.findActive(memberUuid, date);
    }

    /**
     * 움직인 거리 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<Move> findMoveList(String memberUuid, LocalDate date) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkIdExists(memberUuid);

        log.info("[Controller] 움직인 거리 데이터 조회");
        return healthDataRepository.findMove(memberUuid, date);
    }

    /**
     * 걸음수 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<Walk> findWalkList(String memberUuid, LocalDate date) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkIdExists(memberUuid);

        log.info("[Controller] 걸음수 데이터 조회");
        return healthDataRepository.findWalk(memberUuid, date);
    }

    public List<Sleep> findSleepList(String memberUuid, LocalDate date) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkIdExists(memberUuid);

        log.info("[Controller] 수면 데이터 조회");
        return healthDataRepository.findSleep(memberUuid, date);
    }
}
