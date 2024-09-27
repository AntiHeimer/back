package capstone.Antiheimer.feature.member.dto;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;


@Getter
public class InfoResDto extends NormalResDto {

    private final MemberInfoDto memberInfoDto;

    public InfoResDto(String statusCode, String message, MemberInfoDto memberInfoDto) {
        super(statusCode, message);
        this.memberInfoDto = memberInfoDto;
    }
}
