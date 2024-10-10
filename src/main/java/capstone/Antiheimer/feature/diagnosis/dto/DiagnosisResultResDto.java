package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;

@Getter
public class DiagnosisResultResDto {

    private String statusCode;
    private String message;
//    private AiResDto aiResDto;
    private AiSendDto aiSendDto;

    public DiagnosisResultResDto(String statusCode, String message, AiSendDto aiSendDto) {
        this.statusCode=statusCode;
        this.message=message;
        this.aiSendDto=aiSendDto;
    }
}
