package capstone.Antiheimer.feature.relation.dto.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InfoWardDto {

    private String memberUuid;
    private String id;
    private String name;

    public InfoWardDto(String memberUuid, String id, String name) {
        this.memberUuid = memberUuid;
        this.id = id;
        this.name = name;
    }
}
