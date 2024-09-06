package capstone.Antiheimer.feature.health_data;

import capstone.Antiheimer.feature.diagnosis.AiReqDto;
import capstone.Antiheimer.exception.DuplicateHealthDataException;
import capstone.Antiheimer.exception.InvalidDataTypeException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.util.encrypt.AesService;
import capstone.Antiheimer.util.NormalResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HealthDataController {

    @Autowired
    private final HealthDataService healthDataService;
    @Autowired
    private final AesService aesService;

    @Value("${auth.key}")
    private String authKey;

    /**
     * 활동 데이터 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/active")
    public NormalResDto saveActive(@RequestBody SaveActiveReqDto request) {

        try {
            log.info("Active 데이터 저장 시작");
            healthDataService.insertActive(request);

            return new NormalResDto("200", "활동 데이터 저장 성공");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 활동 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 움직인 거리 데이터 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/move")
    public NormalResDto saveMove(@RequestBody SaveMoveReqDto request) {

        try {
            log.info("Move 데이터 저장 시작");
            healthDataService.insertMove(request);

            return new NormalResDto("200", "움직인 거리 데이터 저장 성공");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 움직인 거리 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 걸음수 데이터 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/walk")
    public NormalResDto saveWalk(@RequestBody SaveWalkReqDto request) {

        try {
            log.info("Walk 데이터 저장 시작");
            healthDataService.insertWalk(request);

            return new NormalResDto("200", "걸음수 데이터 저장 성공");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 걸음수 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 몸무게 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/weight")
    public NormalResDto saveWeight(@RequestBody SaveWeightReqDto request) {

        try {
            log.info("Weight 저장 시작");
            healthDataService.insertWeight(request);

            return new NormalResDto("200", "몸무게 저장 성공");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 수면데이터 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/sleep")
    public NormalResDto saveSleep(@RequestBody SaveSleepReqDto request) {

        try {

            log.info("수면 데이터 저장 시작");
            healthDataService.insertSleep(request);

            return new NormalResDto("200", "수면 데이터 저장 성공");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 수면 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 건강 데이터 최근 저장 날짜 조회
     * @param uuid
     * @return
     */
    @GetMapping("/recent")
    public RecentDateRes recentData(@RequestParam("data") String data,
                                    @RequestParam("uuid") String uuid) {

        try {
            log.info("최근 건강 데이터 조회 시작");

            // URL 디코딩
            String decodedUuid = URLDecoder.decode(uuid, StandardCharsets.UTF_8.name());

            // 공백을 +로 변환
            String plusEncodedString = decodedUuid.replace(" ", "+");

            // AES 복호화
            String decryptedUuid = aesService.decryptAES(plusEncodedString);

            LocalDate date = healthDataService.recentDateOfHealthData(decryptedUuid, data);

            return new RecentDateRes("200", "최근 건강 데이터 조회 성공", date);
        } catch (InvalidDataTypeException e) {

            return new RecentDateRes("401", "유효하지 않은 데이터 타입", null);
        } catch (NotExistException e) {

            return new RecentDateRes("408", "존재하지 않는 회원", null);
        } catch (UnsupportedEncodingException e) {

            return new RecentDateRes("410", "디코딩 오류", null);
        }
    }

    @PostMapping("/ai/send/data")
    public HealthDataResDto aiData(@RequestHeader String auth,
                                   @RequestBody AiReqDto request) {

        log.info("권한 확인");

        return null;
//        if (!auth.equals(authKey)) {
//
//            log.warn("권한이 없습니다");
//            return new HealthDataResDto("400", "권한 없음", null, null, null, null, null);
//        }
//
//        log.info("AI서버에 데이터 전송");
//        try {
//            DiagnosisDto diagnosis = healthDataService.sendDataToAi(request);
//            System.out.println("diagnosis = " + diagnosis);
//            // DB에 진단결과 저장
//            log.info("DB에 진단결과 저장");
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
//            log.info("Active 데이터 조회 시작");
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
//            log.info("Move 데이터 조회 시작");
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
//            log.info("Walk 데이터 조회 시작");
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
