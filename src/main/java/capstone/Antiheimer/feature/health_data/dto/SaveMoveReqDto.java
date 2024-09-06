package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveMoveReqDto {

    private String memberUuid;
    private LocalDate date;
    private List<MoveVo> moveData;
}
