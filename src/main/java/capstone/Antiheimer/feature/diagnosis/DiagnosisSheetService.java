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
     * 진단지 1번 무작위 세단어 반환
     * @param num
     * @return
     */
    public List<String> randomWords(int num) {

        checkNumEqualOne(num);  //1번 문제인지 확인

        List<String> words = List.of("연필", "시계", "핸드폰", "아파트", "수건", "냉장고", "가방", "신발", "우산", "세탁기");

        // Stream을 이용하여 랜덤으로 3개의 단어를 추출
        List<String> random = new Random().ints(0, words.size()) // 0부터 words.size() 사이의 랜덤 인덱스 생성
                .distinct()              // 중복을 제거
                .limit(3)                // 3개의 숫자만 가져옴
                .mapToObj(words::get)    // 랜덤으로 생성된 숫자를 사용하여 단어 리스트에서 단어를 가져옴
                .collect(Collectors.toList());  // 추출된 단어들을 리스트로 수집


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
