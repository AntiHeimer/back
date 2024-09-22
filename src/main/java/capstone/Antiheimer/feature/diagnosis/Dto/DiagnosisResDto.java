package capstone.Antiheimer.feature.diagnosis.Dto;

import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class DiagnosisResDto {

    private String statusCode;
    private String message;
    private List<Diagnosis> diagnoisList;

    public DiagnosisResDto(String statusCode, String message, List<Diagnosis> diagnoisList) {
        this.statusCode = statusCode;
        this.message = message;
        this.diagnoisList = diagnoisList;
    }
}
