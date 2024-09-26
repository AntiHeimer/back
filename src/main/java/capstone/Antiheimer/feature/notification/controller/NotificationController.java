package capstone.Antiheimer.feature.notification.controller;

import capstone.Antiheimer.feature.notification.dto.NotificationResDto;
import capstone.Antiheimer.feature.notification.entity.Notification;
import capstone.Antiheimer.feature.notification.service.NotificationService;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 알림 리스트 조회
     * @param memberUuid
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/find-notification")
    public ResponseEntity<NotificationResDto> findNotification(@RequestParam("memberUuid") String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedMemberUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 알림 리스트 조회 시작");
        List<Notification> notificationList = notificationService.findNotification(decryptedMemberUuid);
        // 알림 isRead 변경
        notificationService.changeIsReadNotification(notificationList);

        log.info("[Controller] 알림 리스트 조회 성공");
        return new ResponseEntity<>(new NotificationResDto("200", "알림 리스트 조회 성공", notificationList), HttpStatus.OK);
    }

    /**
     * 알림 삭제
     * @param notificationUuid
     * @return
     * @throws UnsupportedEncodingException
     */
    @DeleteMapping("/delete-notification")
    public ResponseEntity<NormalResDto> deleteNotification(@RequestParam("notificationUuid") String notificationUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(notificationUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedNotificationUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 알림 삭제 시작");
        notificationService.deleteNotification(decryptedNotificationUuid);

        log.info("[Controller] 알림 삭제 성공");
        return new ResponseEntity<>(new NormalResDto("200", "알림 삭제 성공"), HttpStatus.OK);
    }
}
