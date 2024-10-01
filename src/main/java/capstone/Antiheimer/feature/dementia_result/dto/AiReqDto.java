package capstone.Antiheimer.feature.dementia_result.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AiReqDto {

    private String memberUuid;
    private LocalDate date;
}
