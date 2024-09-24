package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DSRandomWordDto {

    private String statusCode;
    private String message;
    private List<String> randomWords;
}
