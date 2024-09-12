package capstone.Antiheimer.feature.relation.dto.save;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveRelationDto {

    private String wardUuid;
    private String guardianUuid;
}
