package capstone.Antiheimer.feature.location.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationReqDto {

    private String memberUuid;
    private LocalDateTime formattedDate;
    private LocationDto location;
}
