package capstone.Antiheimer.feature.member.dto;

import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;


@Getter
public class InfoResDto extends NormalResDto {

    private Member member;

    public InfoResDto(String statusCode, String message, Member member) {
        super(statusCode, message);
        this.member = member;
    }
}
