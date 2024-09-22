package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScoreReqDto {

    private String diagnosisUuid;
    private int num;
    private int score;
}
