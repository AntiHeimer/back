package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DiagnosisSheetResDto {

    private String statusCode;
    private String message;
    private DiagnosisSheet diagnosisSheet;
}
