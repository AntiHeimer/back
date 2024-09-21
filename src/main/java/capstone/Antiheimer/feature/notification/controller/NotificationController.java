package capstone.Antiheimer.feature.notification.controller;

import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.notification.dto.NotificationDto;
import capstone.Antiheimer.feature.notification.dto.NotificationResDto;
import capstone.Antiheimer.feature.notification.service.NotificationService;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final AesService aesService;

    @GetMapping("/find-notification")
    public NotificationResDto findNotification(@RequestParam("memberUuid") String memberUuid) {

        try {
            log.info("알림 조회 시작");

            // URL 디코딩
            String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());

            // 공백을 +로 변환
            String plusEncodedString = decodedUuid.replace(" ", "+");

            // AES 복호화
            String decryptedMemberUuid = aesService.decryptAES(plusEncodedString);
            List<NotificationDto> notificationList = notificationService.findNotificationByUuid(decryptedMemberUuid);

            log.info("알림 조회 성공");
            // 알림 isRead 변경
            notificationService.changeIsReadNotification(notificationList);

            return new NotificationResDto("200", "알림 조회 성공", notificationList);
        } catch (NotExistMemberException e) {

            return new NotificationResDto("408", "존재하지 않는 회원", null);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete-notification")
    public NormalResDto deleteNotification(@RequestParam("notificationUuid") String notificationUuid) {

        try {
            log.info("알림 삭제 시작");

            // URL 디코딩
            String decodedUuid = URLDecoder.decode(notificationUuid, StandardCharsets.UTF_8.name());

            // 공백을 +로 변환
            String plusEncodedString = decodedUuid.replace(" ", "+");

            // AES 복호화
            String decryptedNotificationUuid = aesService.decryptAES(plusEncodedString);
            notificationService.deleteNotification(decryptedNotificationUuid);
            log.info("알림 삭제 성공");

            return new NormalResDto("200", "알림 삭제 성공");
        } catch (NotExistMemberException e) {

            return new NormalResDto("408", "존재하지 않는 알림");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


//    @PostMapping("/send-notification")
//    public String sendNotification(@RequestBody NotificationReq request) {
//        return "Notification sent!";
//    }
}
