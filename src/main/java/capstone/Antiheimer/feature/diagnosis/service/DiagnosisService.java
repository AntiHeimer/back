package capstone.Antiheimer.feature.diagnosis.service;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.exception.invalid.InvalidScoreException;
import capstone.Antiheimer.feature.diagnosis.dto.AiResDto;
import capstone.Antiheimer.feature.diagnosis.dto.AiSendDto;
import capstone.Antiheimer.feature.diagnosis.dto.DementiaResultDto;
import capstone.Antiheimer.feature.diagnosis.entity.Result;
import capstone.Antiheimer.feature.diagnosis.dto.DiagnosisResultReqDto;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcDatabaseDialect;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final CheckService checkService;
    private final ObjectMapper objectMapper;
    private final HealthDataRepository healthDataRepository;

    /**
     * 진단지 문제 반환
     * @param num
     * @return
     */
    public DiagnosisSheet returnDiagnosisSheet(int num) {

        log.info("[Service] 올바른 범위의 문제 번호인지 확인");
        checkNum(num);

        log.info("[Serivce] 진단지 문제 반환");
        return diagnosisRepository.findQuestion(num);
    }


//    /**
//     * 진단 결과 생성
//     * @param memberUuid
//     * @return
//     */
//    @Transactional
//    public String generateDiagnosis(String memberUuid) {
//
//        log.info("[Service] 회원 존재 확인");
//        checkService.checkMemberExists(memberUuid);
//
//        log.info("[Service] 진단 결과 UUID 생성");
//        return diagnosisRepository.generateDiagnosis(memberUuid);
//    }

    /**
     * 진단지 결과 저장
     * @param result
     */
//    @SneakyThrows
    @Transactional
    public AiResDto getDiagnosisResult(DiagnosisResultReqDto result) throws JsonProcessingException {

        int totalScore = 0;
        Map<String, Object> answers = result.getMap();

        log.info("[Service] 멤버 존재 확인");
        checkService.checkMemberExists(result.getMemberUuid());

        log.info("[Service] 답안 채점 및 점수 계산 시작");
        if (answers.containsKey("2")) {
            totalScore += calculateNumTwo((List<String>) answers.get("2"));
        }
        if (answers.containsKey("4")) {
            totalScore += calculateNumFour((List<String>) answers.get("4"));
        }
        totalScore += getScore(answers);

        log.info("[Service] 진단 결과 저장");
        Diagnosis diagnosis = diagnosisRepository.saveDiagnosis(result.getMemberUuid(), totalScore);

        System.out.println("result.getMemberUuid() = " + result.getMemberUuid());
        System.out.println("result = " + result.getMap());

        log.info("[Service] AI 전송 데이터 수집");
        List<Active> activeList = healthDataRepository.findActiveList(result.getMemberUuid(), diagnosis.getDiagnosisDate());
        List<Sleep> sleepList = healthDataRepository.findSleepList(result.getMemberUuid(), diagnosis.getDiagnosisDate());
        List<Walk> walkList = healthDataRepository.findWalkList(result.getMemberUuid(), diagnosis.getDiagnosisDate());
        List<Move> moveList = healthDataRepository.findMoveList(result.getMemberUuid(), diagnosis.getDiagnosisDate());

        AiSendDto aiSendDto = setAiSendDto(totalScore, moveList, walkList, activeList, sleepList);

        log.info("[Service] AI 데이터 전송 시작");
        // 외부 API를 사용하기 위해
        RestTemplate restTemplate = new RestTemplate();

        String aiServerUrl = "https://antiheimer.com/dementia_predict"; //희지한테 ai server url 받기

        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        // 파라미터로 들어온 dto를 JSON 객체로 변환
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body 설정
        String body = objectMapper.writeValueAsString(aiSendDto);

        // Request Message 설정
        HttpEntity<?> requestMessage = new HttpEntity<>(body, headers);

        log.info("AI 서버로 요청 전송");
        System.out.println("request = " + requestMessage);
        // AI 서버로 요청 전송
        HttpEntity<String> response = restTemplate.postForEntity(aiServerUrl, requestMessage, String.class);

        System.out.println("response = " + response);

        log.info("JSON에서 값 추출");
        // JSON에서 값 추출
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        // 지영아 400 떴을 때 에러 처리 부탁해

//        if (jsonNode.get("statusCode").asInt() == 400) {
//             throw new Exception();
//
//        }

        String stage = jsonNode.get("message").get("stage").asText();
        String explanation = jsonNode.get("message").get("exp").asText();

        System.out.println("explanation = " + explanation);
        System.out.println("stage = " + stage);

        log.info("[Service] AI 진단 결과 저장");
        DementiaResultDto resultDto = new DementiaResultDto(result.getMemberUuid(), diagnosis.getDiagnosisDate(), stage, explanation);
        Result dementiaResult = diagnosisRepository.saveResult(resultDto);

        log.info("[Service] AI 진단 데이터 응답 완료");
        System.out.println("diagnosis = " + diagnosis.getScore());
        System.out.println("dementiaResult = " + dementiaResult);
        return new AiResDto(diagnosis.getScore(), dementiaResult);
    }

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Result> findResultList(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 진단 결과 리스트 조회");
        return diagnosisRepository.findResultList(memberUuid);
    }

    /**
     * 2번 답안 채점
     * @param answer
     */
    private int calculateNumTwo(List<String> answer) {

        int score = 0;

        LocalDate now = LocalDate.now();

        int month = Integer.parseInt(answer.get(1));
        int season = 0;
        if (month >= 3 && month <= 5) {
            season = 1;  //봄
        }
        if (month >= 6 && month <= 8) {
            season = 2; //여름
        }
        if (month >= 9 && month <= 11) {
            season = 3; //가을
        }
        if (month == 12 || month == 1 || month == 2){
            season = 4;
        }
        if (Integer.parseInt(answer.get(0)) == now.getYear()) { //년도 비교

            score += 1;
        }
        if (month == now.getMonthValue()) { //월 비교

            score += 1;
        }
        if (Integer.parseInt(answer.get(2)) == now.getDayOfMonth()) { //일 비교

            score += 1;
        }
        if (Integer.parseInt(answer.get(3)) == now.getDayOfWeek().getValue()) { //요일 비교

            score += 1;
        }
        if (Integer.parseInt(answer.get(4)) == season){

            score += 1;
        }

        return score;
    }

    /**
     * 4번 정답 채점
     * @param answer
     * @return
     */
    private int calculateNumFour(List<String> answer) {

        int score = 0;

        if (Integer.parseInt(answer.get(0)) == 93) {

            score += 1;
        }
        if (Integer.parseInt(answer.get(1)) == Integer.parseInt(answer.get(0)) - 7) {

            score += 1;
        }
        if (Integer.parseInt(answer.get(2)) == Integer.parseInt(answer.get(1)) - 7) {

            score += 1;
        }
        if (Integer.parseInt(answer.get(3)) == Integer.parseInt(answer.get(2)) - 7) {

            score += 1;
        }
        if (Integer.parseInt(answer.get(4)) == Integer.parseInt(answer.get(3)) - 7) {

            score += 1;
        }

        return score;
    }

    /**
     * 2, 4번 제외 문제들 점수 합산
     * @param answers
     * @return
     */
    private int getScore(Map<String, Object> answers) {

        int score = 0;
        int i;

        for (i = 1; i < 12; i++) {
            if (i == 2 || i == 4)
                continue;
            validateScores(i, Integer.parseInt((String)answers.get(String.valueOf(i)))); //번호에 따른 점수 유효성 검사
            score += Integer.parseInt((String) answers.get(String.valueOf(i)));
        }

        return score;
    }

//    /**
//     * 점수 입력
//     * @param diagnosisUuid
//     * @param score
//     */
//    @Transactional
//    public void insertScore(String diagnosisUuid, int score) {
//
//        Diagnosis diagnosis = diagnosisRepository.findDiagnosis(diagnosisUuid);
//
//        diagnosis.setScore(score);
//
//        diagnosisRepository.updateDiagnosis(diagnosis);
//    }


//    /**
//     * 답안 점수 계산
//     * @param reqDto
//     */
//    @Transactional
//    public void markAnswer(AnswerReqDto reqDto) {
//
//        log.info("[Service] 정답 확인 문제인지 확인");
//        checkAnswerNum(reqDto.getNum());
//
//        log.info("[Service] 정답 확인");
//        checkAnswer(reqDto);
//    }

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Diagnosis> returnDiagnosis(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 회원 UUID로 진단 결과 리스트 반환");
        List<Diagnosis> diagnosisList = diagnosisRepository.findByMemberUuid(memberUuid);

        log.info("[Service] 진단결과리스트 반환");
        return diagnosisList;
    }

    /**
     * 진단지 문제 번호 확인
     * @param num
     */
    private void checkNum(int num) {

        if (num < 0 || num > 11){
            throw new IncorrectNumException();
        }
    }

//    /**
//     * 점수를 받는 문제 번호가 맞는지 확인
//     * @param num
//     */
//    private void checkScoreNum(int num) {
//        if (num == 2 || num == 4) {
//            throw new IncorrectNumException();
//        }
//    }

//    /**
//     * 정답을 받는 문제 번호가 맞는지 확인
//     * @param num
//     */
//    private void checkAnswerNum(int num) {
//        if (num != 2 && num != 4) {
//            throw new IncorrectNumException();
//        }
//    }

    /**
     * 점수 확인
     * @param num
     * @param score
     */
    private void validateScores(int num, int score) {

        switch (num) {
            case 1: case 6: case 11:
                if (score < 0 || score > 3) {
                    throw new InvalidScoreException();
                }
            case 2: case 3: case 4:
                if (score < 0 || score > 5) {
                    throw new InvalidScoreException();
                }
            case 5:
                if (score < 0 || score > 2) {
                    throw new InvalidScoreException();
                }
            case 7: case 8: case 9: case 10:
                if (score < 0 || score > 1) {
                    throw new InvalidScoreException();
                }
        }
    }

    /**
     * AI 전송 데이터 수집
     * @param score
     * @param moveList
     * @param walkList
     * @param activeList
     * @param sleepList
     * @return
     */
    private AiSendDto setAiSendDto(int score, List<Move> moveList, List<Walk> walkList, List<Active> activeList, List<Sleep> sleepList) {


        List<Double> move = moveList.stream().map(Move::getValue).collect(Collectors.toList());
        List<Integer> walk = walkList.stream().map(Walk::getValue).collect(Collectors.toList());
        List<Integer> active_energy_burned = activeList.stream().map(Active::getActiveEnergyBurned).collect(Collectors.toList());
        List<Integer> deep = sleepList.stream().map(Sleep::getDeep).collect(Collectors.toList());
        List<Integer> rem = sleepList.stream().map(Sleep::getRem).collect(Collectors.toList());
        List<Integer> core = sleepList.stream().map(Sleep::getCore).collect(Collectors.toList());;
        List<Integer> sleep_time = sleepList.stream().map(Sleep::getSleepTime).collect(Collectors.toList());

        int sleepSize = sleepList.size();
        int i;
        List<Integer> awake = new ArrayList<>();

        for(i=0;i<sleepSize;i++){
            awake.add(sleep_time.get(i) - deep.get(i) - rem.get(i) - core.get(i));
        }

        log.info("=====정보 수집 성공=====");

        return new AiSendDto(score, move, walk, active_energy_burned, deep, rem, awake, sleep_time);
    }

//    /**
//     * 정답 확인
//     * @param reqDto
//     */
//    private void checkAnswer(AnswerReqDto reqDto) {
//
//        int score = 0;
//
//        List<String> answer = reqDto.getAnswer();
//
//        if (reqDto.getNum() == 2) {
//
//            LocalDate now = LocalDate.now();
//
//            int month = Integer.valueOf(answer.get(1));
//            int season = 0;
//            if (month >= 3 && month <= 5) {
//                season = 1;  //봄
//            }
//            if (month >=6 && month <= 8) {
//                season = 2; //여름
//            }
//            if (month >=9 && month <= 11) {
//                season = 3; //가을
//            }
//            if (month == 12 || month == 1 || month == 2){
//                season = 4;
//            }
//            if (Integer.valueOf(answer.get(0)) == now.getYear()) { //년도 비교
//
//                score += 1;
//            }
//            if (month == now.getMonthValue()) { //월 비교
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(2)) == now.getDayOfMonth()) { //일 비교
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(3)) == now.getDayOfWeek().getValue()) { //요일 비교
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(4)) == season){
//
//                score += 1;
//            }
//        }
//
//        if(reqDto.getNum() == 4){
//
//            if (Integer.valueOf(answer.get(0)) == 93) {
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(1)) == Integer.valueOf(answer.get(0)) - 7) {
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(2)) == Integer.valueOf(answer.get(1)) - 7) {
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(3)) == Integer.valueOf(answer.get(2)) - 7) {
//
//                score += 1;
//            }
//            if (Integer.valueOf(answer.get(4)) == Integer.valueOf(answer.get(3)) - 7) {
//
//                score += 1;
//            }
//        }
//
//        Diagnosis diagnosis = diagnosisRepository.findDiagnosis(reqDto.getDiagnosisUuid());
//
//        int currentScore = diagnosis.getScore();
//        int newScore = currentScore + score;
//        diagnosis.setScore(newScore);
//        diagnosisRepository.updateDiagnosis(diagnosis);
//    }
}
