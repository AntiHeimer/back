package capstone.Antiheimer.feature.health_data.controller;

import capstone.Antiheimer.feature.diagnosis.Dto.AiReqDto;
import capstone.Antiheimer.feature.health_data.entity.HealthData;
import capstone.Antiheimer.feature.health_data.service.HealthDataService;
import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.util.encrypt.AesService;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HealthDataController {

    @Autowired
    private final HealthDataService healthDataService;
    @Autowired
    private final AesService aesService;

    /**
     * 활동 데이터 저장
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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

    @PostMapping("/find/health-data")
    public ResponseEntity<FindHealthDataResDto> findHealthData(@RequestBody FindHealthDataReqDto reqDto) {

        log.info("[Controller] 건강 데이터 조회 시작");
        List<HealthData> healthDataList = healthDataService.findHealthDataByMemberUuid(reqDto);

        log.info("[Controller] 건강 데이터 조회 성공");
        return new ResponseEntity<>(new FindHealthDataResDto("200", "건강 데이터 조회 성공", healthDataList), HttpStatus.OK);
    }

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

//
//    @GetMapping("/find/active/{uuid}/{date}")
//    public FindActiveResDto findActive(@PathVariable("uuid") String uuid,
//                                       @PathVariable("date") LocalDate date) {
//
//        try {
//            log.info("[Controller] Active 데이터 조회 시작");
//
//            List<Active> activeList = healthDataService.findActiveList(uuid, date);
//
//            return new FindActiveResDto("200", "걸음수 데이터 조회 완료", activeList);
//        } catch (NotExistException e) {
//
//            return new FindActiveResDto("408", "존재하지 않는 회원", null);
//        } catch (NotExistHealthDataException e) {
//
//            return new FindActiveResDto("408", "존재하지 않는 걸음수 데이터", null);
//        }
//    }
//
//    @GetMapping("/find/move/{uuid}/{date}")
//    public FindMoveResDto findMove(@PathVariable("uuid") String uuid,
//                                   @PathVariable("date") LocalDate date) {
//
//        try {
//            log.info("[Controller] Move 데이터 조회 시작");
//
//            List<Move> moveList = healthDataService.findMoveList(uuid, date);
//
//            return new FindMoveResDto("200", "움직인 거리 데이터 조회 완료", moveList);
//        } catch (NotExistException e) {
//
//            return new FindMoveResDto("408", "존재하지 않는 회원", null);
//        } catch (NotExistHealthDataException e) {
//
//            return new FindMoveResDto("408", "존재하지 않는 움직인 거리 데이터", null);
//        }
//    }
//
//    @GetMapping("/find/walk/{uuid}/{date}")
//    public FindWalkResDto findWalk(@PathVariable("uuid") String uuid,
//                                   @PathVariable("date") LocalDate date) {
//
//        try {
//            log.info("[Controller] Walk 데이터 조회 시작");
//
//            List<Walk> walkList = healthDataService.findWalkList(uuid, date);
//
//            return new FindWalkResDto("200", "걸음수 데이터 조회 완료", walkList);
//        } catch (NotExistException e) {
//
//            return new FindWalkResDto("408", "존재하지 않는 회원", null);
//        } catch (NotExistHealthDataException e) {
//
//            return new FindWalkResDto("408", "존재하지 않는 걸음수 데이터", null);
//        }
//    }
}
