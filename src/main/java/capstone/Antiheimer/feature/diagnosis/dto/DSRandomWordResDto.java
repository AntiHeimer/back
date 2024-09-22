package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class DSRandomWordResDto {

    private String statusCode;
    private String message;
    private List<String> randomWords;

    public DSRandomWordResDto(String statusCode, String message, List<String> randomWords) {
        this.statusCode = statusCode;
        this.message = message;
        this.randomWords = randomWords;
    }
}
