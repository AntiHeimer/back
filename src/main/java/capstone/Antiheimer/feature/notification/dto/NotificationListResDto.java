package capstone.Antiheimer.feature.notification.dto;

import capstone.Antiheimer.feature.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationListResDto {

    private String statusCode;
    private String message;
    private List<Notification> notificationList;
}
