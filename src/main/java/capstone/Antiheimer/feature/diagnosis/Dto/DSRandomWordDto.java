package capstone.Antiheimer.feature.diagnosis.Dto;

import java.util.List;

public class DSRandomWordDto {

    private String statusCode;
    private String message;
    private List<String> randomWords;

    public DSRandomWordDto(String statusCode, String message, List<String> randomWords) {

        this.statusCode = statusCode;
        this.message = message;
        this.randomWords = randomWords;
    }
}
