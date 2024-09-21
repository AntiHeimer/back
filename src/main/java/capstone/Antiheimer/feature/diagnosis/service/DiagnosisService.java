package capstone.Antiheimer.feature.diagnosis.service;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.exception.invalid.InvalidScoreException;
import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.diagnosis.repository.DiagnosisRepository;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiagnosisService {

    private final DiagnosisRepository diagnosisSheetRepository;
    private final MemberRepository memberRepository;

    /**
     * 진단지 문제 반환
     * @param num
     * @return
     */
    public DiagnosisSheet returnDiagnosisSheet(int num) {

        checkNum(num); //문제 범위의 번호인지 확인

        return diagnosisSheetRepository.findQuestion(num);
    }

    @Transactional
    public String generateDiagnosis(String uuid) {

        memberExistCheck(uuid); // 회원 존재 확인

        return diagnosisSheetRepository.generateDiagnosis(uuid);
    }

    @Transactional
    public void insertScore(int num, int score) {

        checkNum(num); // 문제 범위의 번호인지 확인
        checkScore(num, score); // 문제의 점수가 유효한지 확인


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

}
