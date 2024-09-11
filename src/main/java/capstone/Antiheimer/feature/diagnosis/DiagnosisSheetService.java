package capstone.Antiheimer.feature.diagnosis;

import capstone.Antiheimer.exception.IncorrectNumException;
import capstone.Antiheimer.feature.diagnosis.domain.DiagnosisSheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiagnosisSheetService {

    private final DiagnosisSheetRepository diagnosisSheetRepository;

    public DiagnosisSheet returnDiagnosisSheet(int num) {

        checkNum(num);

        return diagnosisSheetRepository.findQuestion(num);
    }

    private void checkNum(int num) {

        if(num<0 || num>11){
            throw new IncorrectNumException();
        }
    }
}
