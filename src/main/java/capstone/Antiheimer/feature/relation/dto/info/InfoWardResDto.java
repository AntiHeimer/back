package capstone.Antiheimer.feature.relation.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
public class InfoWardResDto {

    private String statusCode;
    private String message;
    private List<InfoWardDto> infoWardDtoList;

    public InfoWardResDto(String statusCode, String message, List<InfoWardDto> infoWardDtoList) {
        this.statusCode = statusCode;
        this.message = message;
        this.infoWardDtoList = infoWardDtoList;
    }
}
