package capstone.Antiheimer.feature.relation.dto.info;

import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class InfoWardResDto extends NormalResDto {

    private final List<InfoWardDto> infoWardDtoList;

    public InfoWardResDto(String statusCode, String message, List<InfoWardDto> infoWardDtoList) {
        super(statusCode, message);
        this.infoWardDtoList = infoWardDtoList;
    }
}
