package capstone.Antiheimer.feature.diagnosis.controller;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.feature.diagnosis.Dto.StartDiagnosisResDto;
import capstone.Antiheimer.feature.diagnosis.service.DiagnosisService;
import capstone.Antiheimer.feature.diagnosis.Dto.DSRandomWordDto;
import capstone.Antiheimer.feature.diagnosis.Dto.DiagnosisSheetResDto;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiagnosisController {

    @Autowired
    private final DiagnosisService diagnosisService;
    @Autowired
    private final AesService aesService;
    @Autowired
    private final ObjectMapper objectMapper;

    /**
     * 진단지 반환
     * @param num
     * @return
     */
    @GetMapping("/diagnosisSheet")
    public ResponseEntity<DiagnosisSheetResDto> returnDiagnosisSheet(@RequestParam("num") int num) {

        log.info("[Controller] 진단문제 반환 시작");
        DiagnosisSheet diagnosisSheet = diagnosisService.returnDiagnosisSheet(num);

        log.info("[Controller] 진단문제 반환 성공");
        return new ResponseEntity<>(new DiagnosisSheetResDto("200", "진단지 문제 반환 성공", diagnosisSheet), HttpStatus.OK);
    }

    /**
     * 진단지 1번 무작위 세단어 반환
     * @return
     */
    @GetMapping("/diagnosisSheet/word")
    public ResponseEntity<DSRandomWordDto> randomWords() {

        List<String> words = List.of("연필", "시계", "핸드폰", "아파트", "수건", "냉장고", "가방", "신발", "우산", "세탁기");

        log.info("[Controller] 랜덤 세단어 추출 시작");
        // Stream을 이용하여 랜덤으로 3개의 단어를 추출
        List<String> random = new Random().ints(0, words.size()) // 0부터 words.size() 사이의 랜덤 인덱스 생성
                .distinct()              // 중복을 제거
                .limit(3)                // 3개의 숫자만 가져옴
                .mapToObj(words::get)    // 랜덤으로 생성된 숫자를 사용하여 단어 리스트에서 단어를 가져옴
                .collect(Collectors.toList());  // 추출된 단어들을 리스트로 수집

        log.info("[Controller] 랜덤 세단어 추출 성공");
        return new ResponseEntity<>(new DSRandomWordDto("200", "세단어 반환 성공", random), HttpStatus.OK);
    }

    @PostMapping("/diagnosis/start")
    public ResponseEntity<StartDiagnosisResDto> diagnosisStart(@RequestParam("uuid") String uuid) throws JsonProcessingException {

        log.info("[Controller] AES 복호화");
        String decryptedRequest = aesService.decryptAES(uuid);

        log.info("[Controller] 진단 uuid 생성 시작");
        String diagnosisUuid = diagnosisService.generateDiagnosis(decryptedRequest);

        log.info("[Controller] 진단 uuid 생성 성공");
        return new ResponseEntity<>(new StartDiagnosisResDto("200", "진단 uuid 생성 성공", diagnosisUuid), HttpStatus.OK);
    }

//    @PostMapping("/diagnosis/score")
//    public ResponseEntity<NormalResDto> diagnosisScore(@RequestParam("num") int num, @RequestParam("score") int score) {
//
//    }


}
