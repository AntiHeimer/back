package capstone.Antiheimer.feature.notification.controller;

import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.notification.dto.NotificationDto;
import capstone.Antiheimer.feature.notification.dto.NotificationResDto;
import capstone.Antiheimer.feature.notification.service.NotificationService;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/find-notification/{memberUuid}")
    public NotificationResDto findNotification(@PathVariable("memberUuid") String memberUuid) {

        try {
            log.info("알림 조회 시작");
            List<NotificationDto> notificationList = notificationService.findNotificationByUuid(memberUuid);

            log.info("알림 조회 성공");
            log.info("알림 isRead 변경");
            notificationService.changeIsReadNotification(notificationList);

            return new NotificationResDto("200", "알림 조회 성공", notificationList);
        } catch (NotExistException e) {

            return new NotificationResDto("408", "존재하지 않는 회원", null);
        }
    }

    @DeleteMapping("/delete-notification/{notificationUuid}")
    public NormalResDto deleteNotification(@PathVariable("notificationUuid") String notificationUuid) {

        try {
            log.info("알림 삭제");

            notificationService.deleteNotification(notificationUuid);
            return new NormalResDto("200", "알림 삭제 성공");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 알림");
        }
    }


//    @PostMapping("/send-notification")
//    public String sendNotification(@RequestBody NotificationReq request) {
//        return "Notification sent!";
//    }
}
