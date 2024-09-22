package capstone.Antiheimer.feature.diagnosis.service;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.exception.invalid.InvalidScoreException;
import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.diagnosis.Dto.AnswerReqDto;
import capstone.Antiheimer.feature.diagnosis.Dto.ScoreReqDto;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.diagnosis.repository.DiagnosisRepository;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final MemberRepository memberRepository;

    /**
     * 진단지 문제 반환
     * @param num
     * @return
     */
    public DiagnosisSheet returnDiagnosisSheet(int num) {

        checkNum(num); //문제 범위의 번호인지 확인

        return diagnosisRepository.findQuestion(num);
    }


    /**
     * 진단 결과 생성
     * @param uuid
     * @return
     */
    @Transactional
    public String generateDiagnosis(String uuid) {

        memberExistCheck(uuid); // 회원 존재 확인

        return diagnosisRepository.generateDiagnosis(uuid);
    }

    /**
     * 점수 입력
     * @param reqDto
     */
    @Transactional
    public void insertScore(ScoreReqDto reqDto) {

        checkScoreNum(reqDto.getNum()); // 문제 범위의 번호인지 확인
        checkScore(reqDto.getNum(), reqDto.getScore()); // 문제의 점수가 유효한지 확인

        Diagnosis diagnosis = diagnosisRepository.findOneByUuid(reqDto.getDiagnosisUuid());

        int currentScore = diagnosis.getScore();
        int newScore = currentScore + reqDto.getScore();
        diagnosis.setScore(newScore);
        diagnosisRepository.saveDiagnosis(diagnosis);
    }


    /**
     * 답안 점수 계산
     * @param reqDto
     */
    @Transactional
    public void markAnswer(AnswerReqDto reqDto) {

        checkAnswerNum(reqDto.getNum());

        checkAnswer(reqDto);
    }

    /**
     * 진단 결과 조회
     * @param memberUuid
     * @return
     */
    public List<Diagnosis> returnDiagnosis(String memberUuid) {

        memberExistCheck(memberUuid);

        List<Diagnosis> diagnosisList = diagnosisRepository.findByMemberUuid(memberUuid);

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

    /**
     * 점수를 받는 문제 번호가 맞는지 확인
     * @param num
     */
    private void checkScoreNum(int num) {
        if (num == 2 || num == 4) {
            throw new IncorrectNumException();
        }
    }

    /**
     * 정답을 받는 문제 번호가 맞는지 확인
     * @param num
     */
    private void checkAnswerNum(int num) {
        if (num != 2 && num != 4) {
            throw new IncorrectNumException();
        }
    }

    /**
     * 점수 확인
     * @param num
     * @param score
     */
    private void checkScore(int num, int score) {

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
     * 회원 존재 확인
     * @param memberUuid
     */
    private void memberExistCheck(String memberUuid) {

        Member findMember = memberRepository.findOneByUuid(memberUuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistMemberException();
        }
    }

    /**
     * 정답 확인
     * @param reqDto
     */
    private void checkAnswer(AnswerReqDto reqDto) {

        int score = 0;

        List<String> answer = reqDto.getAnswer();

        if (reqDto.getNum() == 2) {

            LocalDate now = LocalDate.now();

            int month = Integer.valueOf(answer.get(1));
            int season = 0;
            if( month>=3 || month <=5) {
                season = 1;
            }
            if( month>=6 || month <=8) {
                season = 2;
            }
            if( month>=9 || month <=11) {
                season = 3;
            }
            if( month==12 || month ==1 || month ==2){
                season = 4;
            }
            if (Integer.valueOf(answer.get(0)) == now.getYear()) { //년도 비교

                score += 1;
            }
            if (month == now.getMonthValue()) { //월 비교

                score += 1;
            }
            if (Integer.valueOf(answer.get(2)) == now.getDayOfMonth()) { //일 비교

                score += 1;
            }
            if (Integer.valueOf(answer.get(3)) == now.getDayOfWeek().getValue()) { //요일 비교

                score += 1;
            }
            if(Integer.valueOf(answer.get(4)) == season){

                score += 1;
            }
        }

        if(reqDto.getNum() == 4){

            if (Integer.valueOf(answer.get(0)) == 93) {

                score += 1;
            }
            if (Integer.valueOf(answer.get(1)) == Integer.valueOf(answer.get(0)) - 7) {

                score += 1;
            }
            if (Integer.valueOf(answer.get(2)) == Integer.valueOf(answer.get(1)) - 7) {

                score += 1;
            }
            if (Integer.valueOf(answer.get(3)) == Integer.valueOf(answer.get(2)) - 7) {

                score += 1;
            }
            if (Integer.valueOf(answer.get(4)) == Integer.valueOf(answer.get(3)) - 7) {

                score += 1;
            }
        }

        Diagnosis diagnosis = diagnosisRepository.findOneByUuid(reqDto.getDiagnosisUuid());

        int currentScore = diagnosis.getScore();
        int newScore = currentScore + score;
        diagnosis.setScore(newScore);
        diagnosisRepository.saveDiagnosis(diagnosis);
    }

}
