package capstone.Antiheimer.feature.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberInfoDto {

    private String id;
    private String name;
    private String gender;
    private LocalDate birth;
    private double weight;
}
