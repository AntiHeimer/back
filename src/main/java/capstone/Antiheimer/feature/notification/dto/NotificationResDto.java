package capstone.Antiheimer.feature.notification.dto;

import capstone.Antiheimer.feature.notification.entity.Notification;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.*;

import java.util.List;

@Getter
public class NotificationResDto extends NormalResDto {

    private final List<Notification> notificationList;

    public NotificationResDto(String statusCode, String message, List<Notification> notificationList) {
        super(statusCode, message);
        this.notificationList = notificationList;
    }
}
