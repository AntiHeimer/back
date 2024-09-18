package capstone.Antiheimer.feature.notification.service;

import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.notification.dto.NotificationDto;
import capstone.Antiheimer.feature.notification.entity.Notification;
import capstone.Antiheimer.feature.notification.repository.NotificationRepository;
import capstone.Antiheimer.feature.relation.dto.RequestRelationReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveRequestNotification(RequestRelationReqDto request) {

        Member toMember = memberRepository.findOneByUuid(request.getToMemberId());

        if (isExist(request.getFromMemberUuid()) && toMember != null) {

            Notification notification = convertToEntity(request, toMember.getUuid());

            log.info("알림 저장");
            notificationRepository.saveNotification(notification);
        } else {

            throw new NotExistException();
        }
    }

    @Transactional
    public void saveGuardianNotification(SaveGuardianReqDto request) {

        Member guardian = memberRepository.findOneByUuid(request.getGuardianUuid());

        if (isExist(request.getWardUuid()) && guardian != null) {

            Notification notification = new Notification();

            notification.setUuid();
            notification.setMemberUuid(request.getWardUuid());
            notification.setFromMemberUuid(guardian.getUuid());
            notification.setFromMemberName(guardian.getName());
            notification.setType("resultGuardian");

            notificationRepository.saveNotification(notification);
        }
    }

    @Transactional
    public void saveWardNotification(SaveWardReqDto request) {

        Member ward = memberRepository.findOneByUuid(request.getWardUuid());

        if (isExist(request.getGuardianUuid()) && ward != null) {

            Notification notification = new Notification();

            notification.setUuid();
            notification.setMemberUuid(request.getGuardianUuid());
            notification.setFromMemberUuid(ward.getUuid());
            notification.setFromMemberName(ward.getName());
            notification.setType("resultWard");

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

    @Transactional
    public void changeIsReadNotification(List<NotificationDto> notificationDtoList) {

        if (notificationDtoList.isEmpty()) {

            log.warn("알림이 존재하지 않습니다");
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
        notification.setType(request.getRequestType());

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
