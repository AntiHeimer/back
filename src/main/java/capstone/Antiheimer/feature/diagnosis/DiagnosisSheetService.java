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
     * 진단지 1번 무작위 세단어 반환
     * @param num
     * @return
     */
    public List<String> randomWords(int num) {

        checkNumEqualOne(num);

        List<String> words = new ArrayList<>();
        words.add("연필");
        words.add("시계");
        words.add("핸드폰");
        words.add("아파트");
        words.add("수건");
        words.add("냉장고");
        words.add("가방");
        words.add("신발");
        words.add("우산");
        words.add("세탁기");

        // 리스트를 랜덤하게 섞음
        Collections.shuffle(words);

        // 섞인 리스트에서 처음 3개 단어 추출
        List<String> random = words.subList(0, 3);

        return random;
    }

    private void checkNum(int num) {

        if(num<0 || num>11){
            throw new IncorrectNumException();
        }
    }

    private void checkNumEqualOne(int num) {

        if (num != 1) {
            throw new IncorrectNumException();
        }
    }
}
