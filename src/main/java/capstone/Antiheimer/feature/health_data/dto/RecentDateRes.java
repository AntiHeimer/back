package capstone.Antiheimer.feature.health_data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RecentDateRes {

    private String statusCode;
    private String message;
    private LocalDate date;
}
