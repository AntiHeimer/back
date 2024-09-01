package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LocationReqDto {

    private String memberUuid;
    private LocalDateTime formattedDate;
    private LocationDto location;
}
