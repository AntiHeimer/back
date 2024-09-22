package capstone.Antiheimer.feature.health_data.controller;

import capstone.Antiheimer.feature.diagnosis.dto.AiReqDto;
import capstone.Antiheimer.feature.health_data.entity.*;
import capstone.Antiheimer.feature.health_data.service.HealthDataService;
import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.util.encrypt.AesService;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthDataController {

    private final HealthDataService healthDataService;
    private final AesService aesService;

    /**
     * 활동 데이터 저장
     * @param reqDto
     * @return NormalResDto
     */
    @PostMapping("/save/active")
    public ResponseEntity<NormalResDto> saveActive(@RequestBody SaveActiveReqDto reqDto) {

        log.info("[Controller] 활동 데이터 저장 시작");
        healthDataService.insertActive(reqDto);

        log.info("[Controller] 활동 데이터 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "활동 데이터 저장 성공"), HttpStatus.OK);
    }

    /**
     * 움직인 거리 데이터 저장
     * @param reqDto
     * @return NormalResDto
     */
    @PostMapping("/save/move")
    public ResponseEntity<NormalResDto> saveMove(@RequestBody SaveMoveReqDto reqDto) {

        log.info("[Controller] 움직인 거리 데이터 저장 시작");
        healthDataService.insertMove(reqDto);

        log.info("[Controller] 움직인 거리 데이터 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "움직인 거리 데이터 저장 성공"), HttpStatus.OK);
    }

    /**
     * 걸음수 데이터 저장
     * @param reqDto
     * @return NormalResDto
     */
    @PostMapping("/save/walk")
    public ResponseEntity<NormalResDto> saveWalk(@RequestBody SaveWalkReqDto reqDto) {

        log.info("[Controller] 걸음수 데이터 저장 시작");
        healthDataService.insertWalk(reqDto);

        log.info("[Controller] 걸음수 데이터 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "걸음수 데이터 저장 성공"), HttpStatus.OK);
    }

    /**
     * 몸무게 저장
     * @param reqDto
     * @return NormalResDto
     */
    @PostMapping("/save/weight")
    public ResponseEntity<NormalResDto> saveWeight(@RequestBody SaveWeightReqDto reqDto) {

        log.info("[Controller] 몸무게 데이터 저장 시작");
        healthDataService.insertWeight(reqDto);

        log.info("[Controller] 몸무게 데이터 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "몸무게 데이터 저장 성공"), HttpStatus.OK);
    }

    /**
     * 수면데이터 저장
     * @param reqDto
     * @return NormalResDto
     */
    @PostMapping("/save/sleep")
    public ResponseEntity<NormalResDto> saveSleep(@RequestBody SaveSleepReqDto reqDto) {

        log.info("[Controller] 수면 데이터 저장 시작");
        healthDataService.insertSleep(reqDto);

        log.info("[Controller] 수면 데이터 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "수면 데이터 저장 성공"), HttpStatus.OK);
    }

    /**
     * 건강 데이터 최근 저장 날짜 조회
     * @param memberUuid
     * @return
     */
    @GetMapping("/recent")
    public ResponseEntity<RecentDateRes> recentData(@RequestParam("data") String data,
                                                    @RequestParam("memberUuid") String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 최근 건강 데이터 날짜 조회 시작");
        LocalDate date = healthDataService.recentDateOfHealthData(decryptedUuid, data);

        log.info("[Controller] 최근 건강 데이터 날짜 조회 성공");
        return new ResponseEntity<>(new RecentDateRes("200", "최근 건강 데이터 날짜 조회 성공", date), HttpStatus.OK);
    }

    /**
     * 건강 데이터 조회
     * @param reqDto
     * @return
     */
    @PostMapping("/find/health-data")
    public ResponseEntity<FindHealthDataResDto> findHealthData(@RequestBody FindHealthDataReqDto reqDto) {

        log.info("[Controller] 건강 데이터 조회 시작");
        List<HealthData> healthDataList = healthDataService.findHealthDataByMemberUuid(reqDto);

        log.info("[Controller] 건강 데이터 조회 성공");
        return new ResponseEntity<>(new FindHealthDataResDto("200", "건강 데이터 조회 성공", healthDataList), HttpStatus.OK);
    }

    /**
     * 활동 데이터 조회
     * @param reqDto
     * @return
     */
    @PostMapping("/find/active")
    public ResponseEntity<FindActiveResDto> findActive(@RequestBody FindActiveReqDto reqDto) {

        log.info("[Controller] 활동 데이터 조회 시작");
        List<Active> activeList = healthDataService.findActiveList(reqDto.getMemberUuid(), reqDto.getDate());

        log.info("[Controller] 활동 데이터 조회 성공");
        return new ResponseEntity<>(new FindActiveResDto("200", "활동 데이터 조회 완료", activeList), HttpStatus.OK);
    }

    /**
     * 움직인 거리 데이터 조회
     * @param reqDto
     * @return
     */
    @PostMapping("/find/move")
    public ResponseEntity<FindMoveResDto> findMove(@RequestBody FindMoveReqDto reqDto) {

        log.info("[Controller] 움직인 거리 데이터 조회 시작");
        List<Move> moveList = healthDataService.findMoveList(reqDto.getMemberUuid(), reqDto.getDate());

        log.info("[Controller] 움직인 거리 데이터 조회 성공");
        return new ResponseEntity<>(new FindMoveResDto("200", "움직인 거리 데이터 조회 완료", moveList), HttpStatus.OK);
    }

    /**
     * 걸음수 데이터 조회
     * @param reqDto
     * @return
     */
    @PostMapping("/find/walk")
    public ResponseEntity<FindWalkResDto> findWalk(@RequestBody FindWalkReqDto reqDto) {

        log.info("[Controller] 걸음수 데이터 조회 시작");
        List<Walk> walkList = healthDataService.findWalkList(reqDto.getMemberUuid(), reqDto.getDate());

        log.info("[Controller] 걸음수 데이터 조회 성공");
        return new ResponseEntity<>(new FindWalkResDto("200", "걸음수 데이터 조회 완료", walkList), HttpStatus.OK);
    }

    /**
     * 수면 데이터 조회
     * @param reqDto
     * @return
     */
    @PostMapping("/find/sleep")
    public ResponseEntity<FindSleepResDto> findSleep(@RequestBody FindSleepReqDto reqDto) {

        log.info("[Controller] 수면 데이터 조회 시작");
        List<Sleep> sleepList = healthDataService.findSleepList(reqDto.getMemberUuid(), reqDto.getDate());

        log.info("[Controller] 수면 데이터 조회 성공");
        return new ResponseEntity<>(new FindSleepResDto("200", "수면 데이터 조회 완료", sleepList), HttpStatus.OK);
    }

    /**
     * AI 진단 결과
     * @param auth
     * @param reqDto
     * @return
     */
    @PostMapping("/ai/send/data")
    public ResponseEntity<HealthDataResDto> aiData(@RequestHeader String auth,
                                                   @RequestBody AiReqDto reqDto) {

        log.info("[Controller] 권한 확인");

        return null;
//        if (!auth.equals(authKey)) {
//
//            log.warn("권한이 없습니다");
//            return new HealthDataResDto("400", "권한 없음", null, null, null, null, null);
//        }
//
//        log.info("[Controller] AI서버에 데이터 전송");
//        try {
//            DiagnosisDto diagnosis = healthDataService.sendDataToAi(reqDto);
//            System.out.println("diagnosis = " + diagnosis);
//            // DB에 진단결과 저장
//            log.info("[Controller] DB에 진단결과 저장");
//            diagnosisService.saveDiagnosis(diagnosis);
//
//        } catch (Exception e) {
//
//        }

    }
}
