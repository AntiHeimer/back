package capstone.Antiheimer.feature.notification.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReqDto {

    private String fromMemberUuid;
    private String toMemberUuid;
    private String requestType;
}
