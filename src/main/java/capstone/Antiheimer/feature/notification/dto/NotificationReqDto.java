package capstone.Antiheimer.feature.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReqDto {

    private String fromMemberUuid;
    private String toMemberId;
}
