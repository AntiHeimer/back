package capstone.Antiheimer.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NormalResDto {

    private String statusCode;
    private String message;

    public NormalResDto(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
