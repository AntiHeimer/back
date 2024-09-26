package capstone.Antiheimer.feature.dementia_result.controller;

import capstone.Antiheimer.feature.diagnosis.dto.AiReqDto;
import capstone.Antiheimer.feature.health_data.dto.HealthDataResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {


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
//            diagnosisService.updateDiagnosis(diagnosis);
//
//        } catch (Exception e) {
//
//        }
    }
}
