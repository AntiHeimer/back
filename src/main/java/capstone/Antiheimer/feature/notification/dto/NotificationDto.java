package capstone.Antiheimer.feature.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {

    private String notificationUuid;
    private String fromMemberUuid;
    private String fromMemberName;
    private boolean isRead;
    private String notificationType;
}