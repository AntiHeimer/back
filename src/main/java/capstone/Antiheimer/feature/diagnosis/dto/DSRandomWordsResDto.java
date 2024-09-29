package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DSRandomWordsResDto extends NormalResDto {

    private final List<String> randomWords;

    public DSRandomWordsResDto(String statusCode, String message, List<String> randomWords) {
        super(statusCode, message);
        this.randomWords = randomWords;
    }
}
