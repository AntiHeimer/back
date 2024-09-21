package capstone.Antiheimer.feature.relation.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class InfoGuardianResDto {

    private String statusCode;
    private String message;
    private List<InfoGuardianDto> infoGuardianDtoList;
}
