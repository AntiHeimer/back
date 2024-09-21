package capstone.Antiheimer.feature.health_data.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SaveMoveReqDto {

    private String memberUuid;
    private LocalDate date;
    private List<MoveVo> moveData;
}
