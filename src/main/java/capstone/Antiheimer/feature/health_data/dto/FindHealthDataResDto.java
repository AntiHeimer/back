package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.HealthData;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FindHealthDataResDto extends NormalResDto {

    private final List<HealthData> healthDataList;

    public FindHealthDataResDto(String statusCode, String message, List<HealthData> healthDataList) {
        super(statusCode, message);
        this.healthDataList = healthDataList;
    }
}
