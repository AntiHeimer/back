package capstone.Antiheimer.feature.diagnosis;

import capstone.Antiheimer.exception.IncorrectNumException;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiagnosisSheetService {

    private final DiagnosisSheetRepository diagnosisSheetRepository;

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
