package capstone.Antiheimer.feature.member.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResDto extends NormalResDto {

    private final String memberUuid;
    private final String jwtToken;

    public LoginResDto(String statusCode, String message, String memberUuid, String jwtToken) {
        super(statusCode, message);
        this.memberUuid = memberUuid;
        this.jwtToken = jwtToken;
    }
}
