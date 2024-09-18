package capstone.Antiheimer.feature.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InfoResDto {

    private String statusCode;
    private String message;
    private String id;
    private String name;
    private String gender;
    private LocalDate birth;

    public InfoResDto(String statusCode, String message, String id, String name, String gender, LocalDate birth) {
        this.statusCode = statusCode;
        this.message = message;
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }
}
