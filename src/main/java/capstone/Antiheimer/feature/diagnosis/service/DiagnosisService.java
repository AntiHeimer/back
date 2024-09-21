package capstone.Antiheimer.feature.diagnosis.service;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.diagnosis.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisSheetRepository;

    /**
     * 진단지 문제 반환
     * @param num
     * @return
     */
    public DiagnosisSheet returnDiagnosisSheet(int num) {

        checkNum(num);

        return diagnosisSheetRepository.findQuestion(num);
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
}
