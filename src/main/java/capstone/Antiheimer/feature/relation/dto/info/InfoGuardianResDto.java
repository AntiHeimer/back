package capstone.Antiheimer.feature.relation.dto.info;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InfoGuardianResDto {

    private String statusCode;
    private String message;
    private List<InfoGuardianDto> infoGuardianDtoList;

    public InfoGuardianResDto(String statusCode, String message, List<InfoGuardianDto> infoGuardianDtoList) {
        this.statusCode = statusCode;
        this.message = message;
        this.infoGuardianDtoList = infoGuardianDtoList;
    }
}
