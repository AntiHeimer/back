package capstone.Antiheimer.feature.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
public class SignupReqDto {

    private String id;
    private String pw;
    private String name;
    private String gender;
    private LocalDate birth;
}
