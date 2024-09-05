package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NotificationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody NotificationReq request) {
        return "Notification sent!";
    }
}
