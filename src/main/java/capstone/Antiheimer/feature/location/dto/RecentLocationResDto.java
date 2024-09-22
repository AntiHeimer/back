package capstone.Antiheimer.feature.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RecentLocationResDto {

    private String statusCode;
    private String message;
    private LocalDateTime date;
    private LocationDto location;
}
