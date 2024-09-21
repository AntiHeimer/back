package capstone.Antiheimer.feature.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NotificationDto {

    private String notificationUuid;
    private String fromMemberUuid;
    private String fromMemberName;
    private boolean isRead;
    private String notificationType;
}