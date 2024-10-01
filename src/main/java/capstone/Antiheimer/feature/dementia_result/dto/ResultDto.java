package capstone.Antiheimer.feature.dementia_result.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class ResultDto {

    private String memberUuid;
    private LocalDate date;
    private int stage;
    private String explanation;

    public ResultDto(String memberUuid, LocalDate date, int stage, String explanation) {
        this.memberUuid = memberUuid;
        this.date = date;
        this.stage = stage;
        this.explanation = explanation;
    }
}
