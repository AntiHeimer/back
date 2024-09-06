package capstone.Antiheimer.feature.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResDto {

    private String fromMemberUuid;
    private String toMemberUuid;
    private String message;
}
