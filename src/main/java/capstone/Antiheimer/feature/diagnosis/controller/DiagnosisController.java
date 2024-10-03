package capstone.Antiheimer.feature.diagnosis.controller;

import capstone.Antiheimer.feature.diagnosis.dto.DementiaResultListResDto;
import capstone.Antiheimer.feature.diagnosis.entity.Result;
import capstone.Antiheimer.feature.diagnosis.dto.*;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.service.DiagnosisService;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;
    private final AesService aesService;
    private final ObjectMapper objectMapper;

    /**
     * 진단지 반환
     * @param num
     * @return
     */
    @GetMapping("/sheet")
    public ResponseEntity<DiagnosisSheetResDto> returnDiagnosisSheet(@RequestParam("num") int num) {

        log.info("[Controller] 진단 문제 반환 시작");
        DiagnosisSheet diagnosisSheet = diagnosisService.returnDiagnosisSheet(num);

        log.info("[Controller] 진단 문제 반환 성공");
        return new ResponseEntity<>(new DiagnosisSheetResDto("200", "진단지 문제 반환 성공", diagnosisSheet), HttpStatus.OK);
    }

    /**
     * 진단지 1번 무작위 세 단어 반환
     * @return
     */
    @GetMapping("/random-words")
    public ResponseEntity<DSRandomWordsResDto> randomWords() {

        List<String> words = List.of("연필", "시계", "핸드폰", "아파트", "수건", "냉장고", "가방", "신발", "우산", "세탁기");

        log.info("[Controller] 랜덤 세 단어 추출 시작");
        // Stream을 이용하여 랜덤으로 3개의 단어를 추출
        List<String> random = new Random().ints(0, words.size()) // 0부터 words.size() 사이의 랜덤 인덱스 생성
                .distinct()              // 중복을 제거
                .limit(3)                // 3개의 숫자만 가져옴
                .mapToObj(words::get)    // 랜덤으로 생성된 숫자를 사용하여 단어 리스트에서 단어를 가져옴
                .collect(Collectors.toList());  // 추출된 단어들을 리스트로 수집

        log.info("[Controller] 랜덤 세 단어 추출 성공");
        return new ResponseEntity<>(new DSRandomWordsResDto("200", "세 단어 반환 성공", random), HttpStatus.OK);
    }

//    /**
//     * 진단 uuid 생성
//     * @param uuid
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    @PostMapping("/start")
//    public ResponseEntity<StartDiagnosisResDto> startDiagnosis(@RequestParam("uuid") String uuid) throws UnsupportedEncodingException {
//
//        log.info("[Controller] 디코딩 및 AES 복호화");
//        // URL 디코딩
//        String decodedUuid = URLDecoder.decode(uuid, StandardCharsets.UTF_8.name());
//        // 공백을 +로 변환
//        String plusEncodedString = decodedUuid.replace(" ", "+");
//        String memberUuid = aesService.decryptAES(plusEncodedString);
//
//        log.info("[Controller] 진단 UUID 생성 시작");
//        String diagnosisUuid = diagnosisService.generateDiagnosis(memberUuid);
//
//        log.info("[Controller] 진단 UUID 생성 성공");
//        return new ResponseEntity<>(new StartDiagnosisResDto("200", "진단 UUID 생성 성공", diagnosisUuid), HttpStatus.OK);
//    }

//    /**
//     * 점수 계산
//     * @param request
//     * @return
//     * @throws UnsupportedEncodingException
//     * @throws JsonProcessingException
//     */
//    @PostMapping("/score")
//    public ResponseEntity<NormalResDto> diagnosisScore(@RequestBody ScoreReqDto request) throws UnsupportedEncodingException, JsonProcessingException {
//
//        log.info("[Controller] 진단 UUID AES 복호화");
//        String decryptedRequest = URLDecoder.decode(request.getDiagnosisUuid(), StandardCharsets.UTF_8.name());
//        ScoreReqDto reqDto = objectMapper.readValue(decryptedRequest, ScoreReqDto.class);
//
//        log.info("[Controller] 점수 저장 시작");
//        diagnosisService.insertScore(reqDto);
//
//        log.info("[Controller] 점수 저장 성공");
//        return new ResponseEntity<>(new NormalResDto("200", "점수 저장 성공"), HttpStatus.OK);
//    }
//
//    /**
//     * 정답 확인 및 점수 계산
//     * @param request
//     * @return
//     * @throws UnsupportedEncodingException
//     * @throws JsonProcessingException
//     */
//    @PostMapping("/answer")
//    public ResponseEntity<NormalResDto> diagnosisAnswer(@RequestBody AnswerReqDto request) throws UnsupportedEncodingException, JsonProcessingException {
//
//        log.info("[Controller] 진단 UUID AES 복호화");
//        String decryptedRequest = URLDecoder.decode(request.getDiagnosisUuid(), StandardCharsets.UTF_8.name());
//        AnswerReqDto reqDto = objectMapper.readValue(decryptedRequest, AnswerReqDto.class);
//
//        log.info("[Controller] 정답 확인 시작");
//        diagnosisService.markAnswer(reqDto);
//
//        log.info("[Controller] 정답 확인 성공");
//        return new ResponseEntity<>(new NormalResDto("200", "정답 확인 성공"), HttpStatus.OK);
//    }

    @PostMapping("/finish")
    public ResponseEntity<DiagnosisResultResDto> diagnosisResult(@RequestBody DiagnosisResultReqDto request) throws UnsupportedEncodingException, JsonProcessingException{

        log.info("[Controller] 진단 UUID AES 복호화");
        String decryptedRequest = URLDecoder.decode(request.getMemberUuid(), StandardCharsets.UTF_8.name());
        DiagnosisResultReqDto reqDto = objectMapper.readValue(decryptedRequest, DiagnosisResultReqDto.class);

        log.info(("[Controller] 답안 채점 및 점수 계산 시작"));
        AiResDto aiResDto = diagnosisService.getDiagnosisResult(reqDto);

        log.info(("[Controller] 답안 채점 및 점수 계산 성공"));
        return new ResponseEntity<>(new DiagnosisResultResDto("200", "진단 결과 저장 및 치매진단 성공", aiResDto), HttpStatus.OK);
    }

    /**
     * 진단 결과 조회
     * @param memberUuid
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/result")
    public ResponseEntity<DiagnosisResDto> diagnosisResultList(@RequestParam String memberUuid) throws UnsupportedEncodingException{

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String uuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 진단 결과 조회 시작");
        List<Diagnosis> diagnosisList = diagnosisService.returnDiagnosis(uuid);

        log.info("[Controller] 진단 결과 조회 성공");
        return new ResponseEntity<>(new DiagnosisResDto("200", "진단 결과 조회 성공", diagnosisList), HttpStatus.OK);
    }

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    @GetMapping("/dementia/find")
    public ResponseEntity<DementiaResultListResDto> findResult(@RequestParam("memberUuid") String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedMemberUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 진단 결과 리스트 조회 시작");
        List<Result> resultList = diagnosisService.findResultList(decryptedMemberUuid);

        log.info("[Controller] 진단 결과 리스트 조회 성공");
        return new ResponseEntity<>(new DementiaResultListResDto("200", "진단 결과 리스트 조회 성공", resultList), HttpStatus.OK);
    }
}
