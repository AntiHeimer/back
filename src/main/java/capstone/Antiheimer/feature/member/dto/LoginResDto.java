package capstone.Antiheimer.feature.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class LoginResDto {

    private String statusCode;
    private String message;
    private String uuid;
    private String jwtToken;
}
