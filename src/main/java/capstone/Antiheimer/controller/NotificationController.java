package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NotificationReq;
import capstone.Antiheimer.firebase.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FcmService fcmService;

    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody NotificationReq request) {
        fcmService.sendNotification(request.getTargetToken(), request.getTitle(), request.getBody());
        return "Notification sent!";
    }
}
