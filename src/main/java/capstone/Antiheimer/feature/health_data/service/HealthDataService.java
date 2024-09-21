package capstone.Antiheimer.feature.health_data.service;

import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.feature.health_data.entity.HealthData;
import capstone.Antiheimer.feature.health_data.repository.HealthDataRepository;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.exception.duplicate.DuplicateHealthDataException;
import capstone.Antiheimer.exception.invalid.InvalidDataTypeException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
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

    /**
     * 활동 데이터 저장
     * - 회원 존재 확인
     * - 활동 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertActive(SaveActiveReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid());
        activeDataDuplicateCheck(reqDto);
        healthDataRepository.saveActive(reqDto);
        log.info("[Controller] Active 데이터 저장 성공");
    }

    /**
     * 움직인 거리 데이터 저장
     * - 회원 존재 확인
     * - 움직인 거리 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertMove(SaveMoveReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid());
        moveDataDuplicateCheck(reqDto);
        healthDataRepository.saveMove(reqDto);
        log.info("[Controller] Move 데이터 저장 성공");
    }

    /**
     * 걸음수 데이터 저장
     * - 회원 존재 확인
     * - 걸음수 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertWalk(SaveWalkReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid());
        walkDataDuplicateCheck(reqDto);
        healthDataRepository.saveWalk(reqDto);
        log.info("[Controller] Walk 데이터 저장 성공");
    }

    /**
     * 몸무게 저장
     * - 회원 존재 확인
     * @param reqDto
     */
    @Transactional
    public void insertWeight(SaveWeightReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid());
        healthDataRepository.saveWeight(reqDto);
        log.info("[Controller] Weight 저장 성공");
    }

    /**
     * 수면 데이터 저장
     * - 회원 존재 확인
     * - 수면 데이터 존재 확인(중복 확인)
     * @param reqDto
     */
    @Transactional
    public void insertSleep(SaveSleepReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid());
        sleepDataDuplicateCheck(reqDto);
        healthDataRepository.saveSleep(reqDto);
        log.info("[Controller] 수면 데이터 저장 성공");
    }

    /**
     * 건강 데이터 최근 저장 날짜 조회
     *
     * @param uuid
     * @param data
     * @return
     */
    public LocalDate recentDateOfHealthData(String memberUuid, String data) {

        memberExistCheck(memberUuid);
        dataTypeExistCheck(data);

        return switch (data) {
            case "active" -> healthDataRepository.findLastSentDateOfActive(memberUuid);
            case "move" -> healthDataRepository.findLastSentDateOfMove(memberUuid);
            case "walk" -> healthDataRepository.findLastSentDateOfWalk(memberUuid);
            case "sleep" -> healthDataRepository.findLastSentDateOfSleep(memberUuid);
            default -> null;
        };
    }

    public List<HealthData> findHealthDataByMemberUuid(FindHealthDataReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid());

        List<HealthData> healthDataList = healthDataRepository.findHealthDataByMemberUuid(reqDto.getMemberUuid(), reqDto.getDate());

        if (healthDataList.isEmpty()) {
            log.warn("건강 데이터가 존재하지 않습니다");
            return null;
        }


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
//

    /**
     * 회원 존재 확인
     * @param uuid
     */
    private void memberExistCheck(String memberUuid) {

        Member findMember = memberRepository.findOneByUuid(memberUuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistMemberException();
        }
    }

    /**
     * 활동 데이터 존재 확인
     * @param reqDto
     */
    private void activeDataDuplicateCheck(SaveActiveReqDto reqDto) {


        if (healthDataRepository.existActive(reqDto)) {

            log.warn("이미 존재하는 활동 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 움직인 거리 데이터 존재 확인
     * @param reqDto
     */
    private void moveDataDuplicateCheck(SaveMoveReqDto reqDto) {

        if (healthDataRepository.existMove(reqDto)) {

            log.warn("이미 존재하는 움직인 거리 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 걸음수 데이터 존재 확인
     * @param reqDto
     */
    private void walkDataDuplicateCheck(SaveWalkReqDto reqDto) {

        if (healthDataRepository.existWalk(reqDto)) {

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
     * @param reqDto
     */
    private void sleepDataDuplicateCheck(SaveSleepReqDto reqDto) {

        if (healthDataRepository.existSleep(reqDto)) {

            log.warn("이미 존재하는 수면 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

}
