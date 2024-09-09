package capstone.Antiheimer.feature.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationReq {

    private String targetToken;
    private String title;
    private String body;
}