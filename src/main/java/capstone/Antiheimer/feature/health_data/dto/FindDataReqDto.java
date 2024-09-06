package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class FindDataReqDto {

    String memberUuid;
    LocalDate date;
}
