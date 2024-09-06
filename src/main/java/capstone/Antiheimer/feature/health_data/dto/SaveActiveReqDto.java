package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SaveActiveReqDto {

    private String memberUuid;
    private LocalDate date;
    private int activeData;
}
