package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Sleep;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FindSleepResDto extends NormalResDto {

    private final List<Sleep> sleepList;

    public FindSleepResDto(String statusCode, String message, List<Sleep> sleepList) {
        super(statusCode, message);
        this.sleepList = sleepList;
    }
}
