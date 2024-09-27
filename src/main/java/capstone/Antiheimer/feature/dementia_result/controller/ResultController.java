package capstone.Antiheimer.feature.dementia_result.controller;

import capstone.Antiheimer.feature.dementia_result.dto.ResultResDto;
import capstone.Antiheimer.feature.dementia_result.entity.Result;
import capstone.Antiheimer.feature.dementia_result.service.ResultService;
import capstone.Antiheimer.feature.diagnosis.dto.AiReqDto;
import capstone.Antiheimer.feature.health_data.dto.HealthDataResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final AesService aesService;

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    @GetMapping("/find")
    public ResponseEntity<ResultResDto> findResult(@RequestParam("memberUuid") String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedMemberUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 진단 결과 리스트 조회 시작");
        List<Result> resultList = resultService.findResultList(decryptedMemberUuid);

        log.info("[Controller] 진단 결과 리스트 조회 성공");
        return new ResponseEntity<>(new ResultResDto("200", "진단 결과 리스트 조회 성공", resultList), HttpStatus.OK);
    }

    /**
     * AI 진단 결과
     * @param auth
     * @param reqDto
     * @return
     */
    @PostMapping("/save") // 지영아 이거 엔드포인트 바꿔도 될듯!
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
//            diagnosisService.updateDiagnosis(diagnosis);
//
//        } catch (Exception e) {
//
//        }
    }
}
