package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import lombok.Getter;

@Getter
public class TestResDto {

    private String statusCode;
    private String message;
    private Diagnosis diagnosis;

    public TestResDto(String statusCode, String message, Diagnosis diagnosis) {
        this.statusCode=statusCode;
        this.message=message;
        this.diagnosis=diagnosis;
    }
}
