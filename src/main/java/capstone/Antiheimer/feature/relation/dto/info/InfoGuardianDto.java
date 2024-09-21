package capstone.Antiheimer.feature.relation.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InfoGuardianDto {

    private String memberUuid;
    private String id;
    private String name;
}
