package capstone.Antiheimer.feature.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class InfoResDto {

    private String statusCode;
    private String message;
    private String id;
    private String name;
    private String gender;
    private LocalDate birth;
}
