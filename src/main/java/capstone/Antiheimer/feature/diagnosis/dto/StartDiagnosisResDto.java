package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class StartDiagnosisResDto {

    private String statusCode;
    private String message;
    private String diagnosisUuid;

    public StartDiagnosisResDto(String statusCode, String message, String diagnosisUuid) {
        this.statusCode = statusCode;
        this.message = message;
        this.diagnosisUuid = diagnosisUuid;
    }
}
