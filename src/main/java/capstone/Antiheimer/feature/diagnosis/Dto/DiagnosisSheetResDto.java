package capstone.Antiheimer.feature.diagnosis.Dto;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
