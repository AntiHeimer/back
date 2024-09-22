package capstone.Antiheimer.feature.diagnosis.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AnswerReqDto {

    private String diagnosisUuid;
    private int num;
    private List<String> answer;
}
