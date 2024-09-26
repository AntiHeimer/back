package capstone.Antiheimer.feature.notification.dto;

import capstone.Antiheimer.feature.notification.entity.Notification;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NotificationResDto {

    private String statusCode;
    private String message;
    private List<Notification> notificationList;
}
