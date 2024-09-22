package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FindWalkReqDto {

    private String memberUuid;
    private LocalDate date;
}
