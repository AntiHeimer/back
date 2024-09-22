package capstone.Antiheimer.feature.relation.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InfoWardDto {

    private String memberUuid;
    private String id;
    private String name;
}