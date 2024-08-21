package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveWalkReqDto {

    private String memberUuid;
    private LocalDate date;
    private List<WalkVo> walkData;
}
