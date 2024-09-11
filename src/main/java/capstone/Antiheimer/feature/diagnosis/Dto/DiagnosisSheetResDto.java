package capstone.Antiheimer.feature.diagnosis.Dto;

import capstone.Antiheimer.feature.diagnosis.domain.DiagnosisSheet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;

@Getter
@NoArgsConstructor
public class DiagnosisSheetResDto {

    private String statusCode;
    private String message;
    private DiagnosisSheet diagnosisSheet;

    public DiagnosisSheetResDto(String statusCode, String message, DiagnosisSheet diagnosisSheet) {
        this.statusCode = statusCode;
        this.message = message;
        this.diagnosisSheet = diagnosisSheet;
    }
}
