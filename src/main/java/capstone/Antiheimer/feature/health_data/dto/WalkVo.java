package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WalkVo {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int value;
}
