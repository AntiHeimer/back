package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResDto {

    private String statusCode;
    private String message;
    private String uuid;
    private String jwtToken;

    public LoginResDto(String statusCode, String message, String uuid, String jwtToken) {
        this.statusCode = statusCode;
        this.message = message;
        this.uuid = uuid;
        this.jwtToken = jwtToken;
    }
}
