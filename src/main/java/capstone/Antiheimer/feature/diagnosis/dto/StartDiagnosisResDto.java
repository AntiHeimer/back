package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

@Getter
public class StartDiagnosisResDto extends NormalResDto {

    private final String diagnosisUuid;

    public StartDiagnosisResDto(String statusCode, String message, String diagnosisUuid) {
        super(statusCode, message);
        this.diagnosisUuid = diagnosisUuid;
    }
}
