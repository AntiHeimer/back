package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SleepVo {

    private LocalDateTime endDate;
    private String id;
    private String sourceId;
    private String sourceName;
    private LocalDateTime startDate;
    private String value;
}
