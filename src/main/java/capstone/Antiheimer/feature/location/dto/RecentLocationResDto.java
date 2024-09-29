package capstone.Antiheimer.feature.location.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecentLocationResDto extends NormalResDto {

    private final LocalDateTime date;
    private final LocationDto location;

    public RecentLocationResDto(String statusCode, String message, LocalDateTime date, LocationDto location) {
        super(statusCode, message);
        this.date = date;
        this.location = location;
    }
}
