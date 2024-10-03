package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class DiagnosisResultReqDto {

    private String memberUuid;
    private Map<String, Object> map;
}
