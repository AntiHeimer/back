package capstone.Antiheimer.feature.dementia_result.dto;

import capstone.Antiheimer.feature.dementia_result.entity.Result;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiResDto {

    private String statusCode;
    private String message;
    private Result result;

    public AiResDto(String statusCode, String message, Result result) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }
}
