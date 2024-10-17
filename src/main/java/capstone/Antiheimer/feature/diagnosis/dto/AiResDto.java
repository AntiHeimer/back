package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.Result;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiResDto {

    private String memberName;
    private int diagnosisScore;
    private Result result;

    public AiResDto(String memberName, int diagnosisScore, Result result) {
        this.memberName=memberName;
        this.diagnosisScore = diagnosisScore;
        this.result = result;
    }
}
