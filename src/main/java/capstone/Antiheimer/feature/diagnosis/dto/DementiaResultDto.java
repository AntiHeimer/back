package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DementiaResultDto {

    private String memberUuid;
    private LocalDate date;
    private int stage;
    private String explanation;

    public DementiaResultDto(String memberUuid, LocalDate date, int stage, String explanation) {
        this.memberUuid = memberUuid;
        this.date = date;
        this.stage = stage;
        this.explanation = explanation;
    }
}
