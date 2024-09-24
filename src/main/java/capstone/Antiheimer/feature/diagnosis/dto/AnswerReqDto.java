package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AnswerReqDto {

    private String diagnosisUuid;
    private int num;
    private List<String> answer;
}
