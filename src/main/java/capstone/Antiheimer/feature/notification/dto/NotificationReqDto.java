package capstone.Antiheimer.feature.notification.dto;

import lombok.*;

@Getter
public class NotificationReqDto {

    private String fromMemberUuid;
    private String toMemberUuid;
    private String requestType;
}
