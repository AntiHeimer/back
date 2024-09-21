package capstone.Antiheimer.feature.notification.service;

import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.exception.notexist.NotExistNotificationException;
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
    public void saveRequestNotification(RequestRelationReqDto reqDto) {

        Member toMember = memberRepository.findOneById(reqDto.getToMemberId());

        if (isExist(reqDto.getFromMemberUuid()) && toMember != null) {

            Notification notification = convertToEntity(reqDto, toMember.getUuid());

            log.info("[Controller] 알림 저장");
            notificationRepository.saveNotification(notification);
        } else {

            throw new NotExistMemberException();
        }
    }

    @Transactional
    public void saveGuardianNotification(SaveGuardianReqDto reqDto) {

        Member guardian = memberRepository.findOneByUuid(reqDto.getGuardianUuid());

        if (isExist(reqDto.getWardUuid()) && guardian != null) {

            Notification notification = new Notification();

            notification.setUuid();
            notification.setMemberUuid(reqDto.getWardUuid());
            notification.setFromMemberUuid(guardian.getUuid());
            notification.setFromMemberName(guardian.getName());
            notification.setType("resultGuardian");

            notificationRepository.saveNotification(notification);
        }
    }

    @Transactional
    public void saveWardNotification(SaveWardReqDto reqDto) {

        Member ward = memberRepository.findOneByUuid(reqDto.getWardUuid());

        if (isExist(reqDto.getGuardianUuid()) && ward != null) {

            Notification notification = new Notification();

            notification.setUuid();
            notification.setMemberUuid(reqDto.getGuardianUuid());
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
            throw new NotExistNotificationException();
        }
    }

    public List<NotificationDto> findNotificationByUuid(String memberUuid) {

        if (isExist(memberUuid)) {

            return notificationRepository.findNotificationListByUuid(memberUuid);
        } else {

            log.warn("회원이 존재하지 않습니다");
            throw new NotExistMemberException();
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

    public Notification convertToEntity(RequestRelationReqDto reqDto, String toMemberUuid) {

        Notification notification = new Notification();

        Member fromMember = memberRepository.findOneByUuid(reqDto.getFromMemberUuid());

        notification.setUuid();
        notification.setMemberUuid(toMemberUuid);
        notification.setFromMemberUuid(reqDto.getFromMemberUuid());
        notification.setFromMemberName(fromMember.getName());
        notification.setType(reqDto.getRequestType());

        return notification;
    }

    public boolean isExist(String memberUuid) {

        Member findMember = memberRepository.findOneByUuid(memberUuid);

        if (findMember == null) {

            log.warn("회원이 존재하지 않습니다");
            throw new NotExistMemberException();
        } else {
            return true;
        }
    }
}
