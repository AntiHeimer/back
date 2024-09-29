package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Walk;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FindWalkResDto extends NormalResDto {

    private final List<Walk> walkList;

    public FindWalkResDto(String statusCode, String message, List<Walk> walkList) {
        super(statusCode, message);
        this.walkList = walkList;
    }
}
