package capstone.Antiheimer.feature.relation.dto.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InfoGuardianDto {

    private String uuid;
    private String id;
    private String name;

    public InfoGuardianDto(String uuid, String id, String name) {
        this.uuid = uuid;
        this.id = id;
        this.name = name;
    }
}
