package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class DiagnosisSheetResDto {

    private String statusCode;
    private String message;
    private DiagnosisSheet diagnosisSheet;
}
