package capstone.Antiheimer.feature.relation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestRelationReqDto {

    private String fromMemberUuid;
    private String toMemberId;
    private String requestType;
}
