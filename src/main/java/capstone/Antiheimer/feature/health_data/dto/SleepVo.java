package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SleepVo {

    private LocalDateTime endDateTime;
    private LocalDateTime startDateTime;
    private String value;
}
