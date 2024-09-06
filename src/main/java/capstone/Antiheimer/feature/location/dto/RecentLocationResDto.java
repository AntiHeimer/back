package capstone.Antiheimer.feature.location.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecentLocationResDto {

    private String statusCode;
    private String message;
    private LocalDateTime date;
    private LocationDto location;

    public RecentLocationResDto(String statusCode, String message, LocalDateTime date, LocationDto location) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = date;
        this.location = location;
    }
}
