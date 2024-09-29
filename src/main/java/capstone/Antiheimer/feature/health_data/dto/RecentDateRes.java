package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecentDateRes extends NormalResDto {

    private final LocalDate date;

    public RecentDateRes(String statusCode, String message, LocalDate date) {
        super(statusCode, message);
        this.date = date;
    }
}
