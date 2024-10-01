package capstone.Antiheimer.feature.dementia_result.service;

import capstone.Antiheimer.feature.dementia_result.dto.AiReqDto;
import capstone.Antiheimer.feature.dementia_result.dto.AiSendDto;
import capstone.Antiheimer.feature.dementia_result.dto.ResultDto;
import capstone.Antiheimer.feature.dementia_result.entity.Result;
import capstone.Antiheimer.feature.dementia_result.repository.DementiaResultRepository;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.repository.DiagnosisRepository;
import capstone.Antiheimer.feature.health_data.entity.Active;
import capstone.Antiheimer.feature.health_data.entity.Move;
import capstone.Antiheimer.feature.health_data.entity.Sleep;
import capstone.Antiheimer.feature.health_data.entity.Walk;
import capstone.Antiheimer.feature.health_data.repository.HealthDataRepository;
import capstone.Antiheimer.util.CheckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DementiaResultService {

    private final DementiaResultRepository dementiaResultRepository;
    private final CheckService checkService;
    private final DiagnosisRepository diagnosisRepository;
    private final HealthDataRepository healthDataRepository;
    private final ObjectMapper objectMapper;

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Result> findResultList(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 진단 결과 리스트 조회");
        return dementiaResultRepository.findResultList(memberUuid);
    }

    /**
     * AI 데이터 전송
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public Result sendToAi(AiReqDto request) throws JsonProcessingException {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(request.getMemberUuid());

        log.info("[Service] AI 전송 데이터 수집");
        Diagnosis diagnosis = diagnosisRepository.findRecentOneByMemberUuid(request.getMemberUuid());
        List<Active> activeList = healthDataRepository.findActiveList(request.getMemberUuid(), request.getDate());
        List<Sleep> sleepList = healthDataRepository.findSleepList(request.getMemberUuid(), request.getDate());
        List<Walk> walkList = healthDataRepository.findWalkList(request.getMemberUuid(), request.getDate());
        List<Move> moveList = healthDataRepository.findMoveList(request.getMemberUuid(), request.getDate());
        AiSendDto aiSendDto = new AiSendDto(request.getMemberUuid(), diagnosis.getScore(), activeList, sleepList, walkList, moveList);

        log.info("=====Ai 데이터 전송 시작=====");
        // 외부 API를 사용하기 위해
        RestTemplate restTemplate = new RestTemplate();

        String aiServerUrl = "https://aiurl 필요해용";

        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        // 파라미터로 들어온 dto를 JSON 객체로 변환
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body 설정
        String body = objectMapper.writeValueAsString(aiSendDto);

        // Request Message 설정
        HttpEntity<?> requestMessage = new HttpEntity<>(body, headers);

        log.info("AI서버로 요청 전송");
        // AI서버로 요청 전송
        HttpEntity<String> response = restTemplate.postForEntity(aiServerUrl, requestMessage, String.class);

        log.info("JSON에서 값 추출");
        // JSON에서 값 추출
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        System.out.println("response.getBody() = " + response.getBody());

        String memberUuid = jsonNode.get("memberUuid").asText();
        LocalDate date = LocalDate.parse(jsonNode.get("date").asText());
        int stage = jsonNode.get("stage").asInt();
        String explanation = jsonNode.get("explanation").asText();

        log.info("=====AI 데이터 전송 완료=====");
        ResultDto resultDto = new ResultDto(memberUuid, date, stage, explanation);

        log.info("[Service]AI 결과 저장");
        Result result = dementiaResultRepository.saveResult(resultDto);

        return result;
    }
}
