package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MoveVo {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int value;
}
