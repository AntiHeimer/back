package capstone.Antiheimer.feature.diagnosis.service;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.exception.invalid.InvalidScoreException;
import capstone.Antiheimer.feature.diagnosis.dto.DiagnosisResultReqDto;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.diagnosis.repository.DiagnosisRepository;
import capstone.Antiheimer.util.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final CheckService checkService;

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


    /**
     * 진단 결과 생성
     * @param memberUuid
     * @return
     */
    @Transactional
    public String generateDiagnosis(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 진단 결과 UUID 생성");
        return diagnosisRepository.generateDiagnosis(memberUuid);
    }

    /**
     * 진단지 결과 저장
     * @param result
     */
    @Transactional
    public void getDiagnosisResult(DiagnosisResultReqDto result) {

        int totalScore = 0;
        Map<String, Object> answers = result.getMap();

        log.info("[Service] 진단 존재 확인");
        checkService.checkDiagnosisExist(result.getDiagnosisUuid());

        log.info("[Service] 답안 채점 및 점수 계산 시작");
        if (answers.containsKey("2")) {
            totalScore += calculateNumTwo((List<String>) answers.get("2"));
        }
        if (answers.containsKey("4")) {
            totalScore += calculateNumFour((List<String>) answers.get("4"));
        }
        totalScore += getScore(answers);

        log.info("[Service] 총 점수 입력");
        Diagnosis diagnosis = diagnosisRepository.findDiagnosis(result.getDiagnosisUuid());
        diagnosis.setScore(totalScore);
        diagnosisRepository.updateDiagnosis(diagnosis);
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
        if (month >=6 && month <= 8) {
            season = 2; //여름
        }
        if (month >=9 && month <= 11) {
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
            if(i==2 || i==4)
                continue;
            validateScores(i, Integer.parseInt((String) answers.get(i))); //번호에 따른 점수 유효성 검사
            score += Integer.parseInt((String) answers.get(i));
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

        if(num<0 || num>11){
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
