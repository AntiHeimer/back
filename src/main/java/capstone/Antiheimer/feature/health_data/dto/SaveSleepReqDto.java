package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveSleepReqDto {

    private String memberUuid;
    private LocalDate date;
    private List<SleepVo> sleepData;
}
