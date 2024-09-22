package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class DiagnosisResDto {

    private String statusCode;
    private String message;
    private List<Diagnosis> diagnoisList;
}
