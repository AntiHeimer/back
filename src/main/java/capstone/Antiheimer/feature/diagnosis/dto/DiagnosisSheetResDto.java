package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

@Getter
public class DiagnosisSheetResDto extends NormalResDto {

    private DiagnosisSheet diagnosisSheet;

    public DiagnosisSheetResDto(String statusCode, String message, DiagnosisSheet diagnosisSheet) {
        super(statusCode, message);
        this.diagnosisSheet = diagnosisSheet;
    }
}
