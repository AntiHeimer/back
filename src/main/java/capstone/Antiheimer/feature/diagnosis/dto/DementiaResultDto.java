package capstone.Antiheimer.feature.diagnosis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DementiaResultDto {

    private String memberUuid;
    private LocalDate date;
    private String stage;
    private String explanation;

    public DementiaResultDto(String memberUuid, LocalDate date, String stage, String explanation) {
        this.memberUuid = memberUuid;
        this.date = date;
        this.stage = stage;
        this.explanation = explanation;
    }
}
