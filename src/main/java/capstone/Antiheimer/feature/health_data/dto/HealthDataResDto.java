package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

@Getter
public class HealthDataResDto extends NormalResDto {

    private final String memberUuid;

    public HealthDataResDto(String statusCode, String message, String memberUuid) {
        super(statusCode, message);
        this.memberUuid = memberUuid;
    }
}
