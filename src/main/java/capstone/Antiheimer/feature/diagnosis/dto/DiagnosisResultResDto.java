package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;

@Getter
public class DiagnosisResultResDto {

    private String statusCode;
    private String message;
    private AiResDto aiResDto;

    public DiagnosisResultResDto(String statusCode, String message, AiResDto aiResDto) {
        this.statusCode=statusCode;
        this.message=message;
        this.aiResDto=aiResDto;
    }
}
