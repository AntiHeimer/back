package capstone.Antiheimer.feature.relation.dto;

import lombok.Getter;

@Getter
public class RequestRelationReqDto {

    private String fromMemberUuid;
    private String toMemberId;
    private String requestType;
}
