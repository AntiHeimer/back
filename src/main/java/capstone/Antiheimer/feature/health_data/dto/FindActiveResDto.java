package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Active;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FindActiveResDto extends NormalResDto {

    private final List<Active> activeList;

    public FindActiveResDto(String statusCode, String message, List<Active> activeList) {
        super(statusCode, message);
        this.activeList = activeList;
    }
}
