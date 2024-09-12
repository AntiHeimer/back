package capstone.Antiheimer.feature.notification.service;

import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.notification.dto.NotificationDto;
import capstone.Antiheimer.feature.notification.entity.Notification;
import capstone.Antiheimer.feature.notification.repository.NotificationRepository;
import capstone.Antiheimer.feature.relation.dto.RequestRelationReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.valueOf;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveNotification(RequestRelationReqDto request) {

        Member toMember = memberRepository.findOneById(request.getToMemberId());

        System.out.println("toMember = " + toMember);

        if (isExist(request.getFromMemberUuid()) && toMember != null) {

            Notification notification = convertToEntity(request, toMember.getUuid());

            log.info("알림 저장");
            notificationRepository.saveNotification(notification);
        }
    }

    @Transactional
    public void deleteNotification(String notificationUuid) {

        Notification notification = notificationRepository.findNotificationByUuid(notificationUuid);

        if (notification != null) {

            notificationRepository.deleteNotification(notification);
        } else {

            log.warn("알림이 존재하지 않습니다");
            throw new NotExistException();
        }
    }

    public List<NotificationDto> findNotificationByUuid(String memberUuid) {

        if (isExist(memberUuid)) {

            return notificationRepository.findNotificationListByUuid(memberUuid);
        } else {

            log.warn("회원이 존재하지 않습니다");
            throw new NotExistException();
        }
    }

    public void changeIsReadNotification(List<NotificationDto> notificationDtoList) {

        if (notificationDtoList.isEmpty()) {

            log.warn("알림이 존재하지 않습니다");
            throw new NotExistException();
        } else {
            notificationRepository.changeIsReadNotification(notificationDtoList);
        }
    }

    public Notification convertToEntity(RequestRelationReqDto request, String toMemberUuid) {

        Notification notification = new Notification();

        Member fromMember = memberRepository.findOneByUuid(request.getFromMemberUuid());

        notification.setUuid();
        notification.setMemberUuid(toMemberUuid);
        notification.setFromMemberUuid(request.getFromMemberUuid());
        notification.setFromMemberName(fromMember.getName());
        System.out.println("Notification.NotificationType.valueOf(request.getRequestType()) = " + Notification.NotificationType.valueOf(request.getRequestType()));
        notification type = Notification.NotificationType.valueOf(request.getRequestType());

        return notification;
    }

    public boolean isExist(String memberUuid) {

        Member findMember = memberRepository.findOneByUuid(memberUuid);

        if (findMember == null) {

            log.warn("회원이 존재하지 않습니다");
            throw new NotExistException();
        } else {
            return true;
        }
    }
}
