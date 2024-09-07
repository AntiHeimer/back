package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Walk;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindWalkResDto {

    private String statusCode;
    private String message;
    private List<Walk> walkList;

    public FindWalkResDto(String statusCode, String message, List<Walk> walkList) {
        this.statusCode = statusCode;
        this.message = message;
        this.walkList = walkList;
    }
}
