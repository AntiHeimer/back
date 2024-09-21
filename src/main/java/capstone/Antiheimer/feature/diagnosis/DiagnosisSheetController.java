package capstone.Antiheimer.feature.diagnosis;

import capstone.Antiheimer.exception.incorrect.IncorrectNumException;
import capstone.Antiheimer.feature.diagnosis.dto.DSRandomWordDto;
import capstone.Antiheimer.feature.diagnosis.dto.DiagnosisSheetResDto;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiagnosisSheetController {

    private final DiagnosisSheetService diagnosisSheetService;

    /**
     * 진단지 반환
     * @param num
     * @return
     */
    @GetMapping("/diagnosisSheet")
    public DiagnosisSheetResDto returnDiagnosisSheet(@RequestParam("num") int num) {

        try {
            log.info("[Service] 진단문제 반환 시작");

            DiagnosisSheet diagnosisSheet = diagnosisSheetService.returnDiagnosisSheet(num);

            return new DiagnosisSheetResDto("200", "진단지 문제 반환 성공", diagnosisSheet);
        } catch (IncorrectNumException e){

            return new DiagnosisSheetResDto("409", "잘못된 번호", null);
        }
    }

    /**
     * 진단지 1번 무작위 세단어 반환
     * @return
     */
    @GetMapping("/diagnosisSheet/word")
    public DSRandomWordDto randomWords() {

        List<String> words = List.of("연필", "시계", "핸드폰", "아파트", "수건", "냉장고", "가방", "신발", "우산", "세탁기");

        // Stream을 이용하여 랜덤으로 3개의 단어를 추출
        List<String> random = new Random().ints(0, words.size()) // 0부터 words.size() 사이의 랜덤 인덱스 생성
                .distinct()              // 중복을 제거
                .limit(3)                // 3개의 숫자만 가져옴
                .mapToObj(words::get)    // 랜덤으로 생성된 숫자를 사용하여 단어 리스트에서 단어를 가져옴
                .collect(Collectors.toList());  // 추출된 단어들을 리스트로 수집


        return new DSRandomWordDto("200", "세단어 반환 성공", random);
    }

}
