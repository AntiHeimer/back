package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SaveWalkReqDto {

    private String memberUuid;
    private LocalDate date;
    private List<WalkVo> walkData;
}
