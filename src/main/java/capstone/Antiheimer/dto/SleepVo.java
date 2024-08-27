package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SleepVo {

    private LocalDateTime endDateTime;
    private LocalDateTime startDateTime;
    private String value;
}
