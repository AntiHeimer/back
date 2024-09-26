package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DSRandomWordsResDto {

    private String statusCode;
    private String message;
    private List<String> randomWords;

}
