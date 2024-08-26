package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SleepVo {

    private LocalDateTime endDateTime;
    private String id;
    private String sourceId;
    private String sourceName;
    private LocalDateTime startDateTime;
    private String value;
}
