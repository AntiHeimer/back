package capstone.Antiheimer.feature.diagnosis;

import capstone.Antiheimer.exception.IncorrectNumException;
import capstone.Antiheimer.feature.diagnosis.Dto.DSRandomWordDto;
import capstone.Antiheimer.feature.diagnosis.Dto.DiagnosisSheetResDto;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            log.info("진단문제 반환 시작");

            DiagnosisSheet diagnosisSheet = diagnosisSheetService.returnDiagnosisSheet(num);

            return new DiagnosisSheetResDto("200", "진단지 문제 반환 성공", diagnosisSheet);
        } catch (IncorrectNumException e){

            return new DiagnosisSheetResDto("409", "잘못된 번호", null);
        }
    }

    @GetMapping("/diagnosisSheet/num1")
    public DSRandomWordDto RandomWords(@RequestParam("num") int num) {

        try {
            log.info("진단지 1번 세단어 랜덤 반환 시작");

            List<String> Words = diagnosisSheetService.randomWords(num);

            return new DSRandomWordDto("200", "진단지 1번 랜덤 세단어 반환 성공", Words);
        } catch (IncorrectNumException e) {

            return new DSRandomWordDto("409", "잘못된 번호", null);
        }
    }

}
