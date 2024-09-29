package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;
@Getter
public class DiagnosisResDto extends NormalResDto {

    private final List<Diagnosis> diagnoisList;

    public DiagnosisResDto(String statusCode, String message, List<Diagnosis> diagnoisList) {
        super(statusCode, message);
        this.diagnoisList = diagnoisList;
    }
}
