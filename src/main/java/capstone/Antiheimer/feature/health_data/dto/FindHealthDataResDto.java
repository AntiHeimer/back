package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Active;
import capstone.Antiheimer.feature.health_data.entity.HealthData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindHealthDataResDto {

    private String statusCode;
    private String message;
    private List<HealthData> healthDataList;

    public FindHealthDataResDto(String statusCode, String message, List<HealthData> healthDataList) {
        this.statusCode = statusCode;
        this.message = message;
        this.healthDataList = healthDataList;
    }
}
