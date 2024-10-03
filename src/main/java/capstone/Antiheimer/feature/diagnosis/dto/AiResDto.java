package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.Result;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiResDto {

    private int diagnosisScore;
    private Result result;

    public AiResDto(int diagnosisScore, Result result) {
        this.diagnosisScore=diagnosisScore;
        this.result = result;
    }
}
