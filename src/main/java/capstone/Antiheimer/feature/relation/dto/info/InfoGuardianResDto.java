package capstone.Antiheimer.feature.relation.dto.info;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class InfoGuardianResDto extends NormalResDto {

    private final List<InfoGuardianDto> infoGuardianDtoList;

    public InfoGuardianResDto(String statusCode, String message, List<InfoGuardianDto> infoGuardianDtoList) {
        super(statusCode, message);
        this.infoGuardianDtoList = infoGuardianDtoList;
    }
}
