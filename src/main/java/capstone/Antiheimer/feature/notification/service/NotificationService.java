package capstone.Antiheimer.feature.notification.service;

import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.notification.entity.Notification;
import capstone.Antiheimer.feature.notification.repository.NotificationRepository;
import capstone.Antiheimer.feature.relation.dto.RequestRelationReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import capstone.Antiheimer.util.CheckService;
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

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final CheckService checkService;

    /**
     * 요청 알림 저장
     * @param reqDto
     */
    @Transactional
    public void saveRequestNotification(RequestRelationReqDto reqDto) {

        log.info("[Service] 아이디 존재 확인");
        checkService.checkIdExists(reqDto.getToMemberId());
        Member toMember = memberRepository.findOneById(reqDto.getToMemberId());

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getFromMemberUuid());
        checkService.checkMemberExists(toMember.getUuid());

        Notification notification = convertToEntity(reqDto, toMember.getUuid());

        log.info("[Service] 알림 저장");
        notificationRepository.saveNotification(notification);
    }

    /**
     * 보호자 요청 알림 저장
     * @param reqDto
     */
    @Transactional
    public void saveGuardianNotification(SaveGuardianReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getGuardianUuid());
        checkService.checkMemberExists(reqDto.getWardUuid());

        Member guardian = memberRepository.findOneByUuid(reqDto.getGuardianUuid());

        Notification notification = new Notification();

        notification.setUuid();
        notification.setMemberUuid(reqDto.getWardUuid());
        notification.setFromMemberUuid(guardian.getUuid());
        notification.setFromMemberName(guardian.getName());
        notification.setType("resultGuardian");

        log.info("[Service] 보호자 저장");
        notificationRepository.saveNotification(notification);
    }

    /**
     * 피보호자 요청 알림 저장
     * @param reqDto
     */
    @Transactional
    public void saveWardNotification(SaveWardReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getGuardianUuid());
        checkService.checkMemberExists(reqDto.getWardUuid());

        Member ward = memberRepository.findOneByUuid(reqDto.getWardUuid());

        Notification notification = new Notification();

        notification.setUuid();
        notification.setMemberUuid(reqDto.getGuardianUuid());
        notification.setFromMemberUuid(ward.getUuid());
        notification.setFromMemberName(ward.getName());
        notification.setType("resultWard");

        log.info("[Service] 피보호자 저장");
        notificationRepository.saveNotification(notification);
    }

    /**
     * 알림 삭제
     * @param notificationUuid
     */
    @Transactional
    public void deleteNotification(String notificationUuid) {

        Notification notification = notificationRepository.findNotification(notificationUuid);

        log.info("[Service] 알림 존재 확인");
        checkService.checkNotificationExists(notification);
        notificationRepository.deleteNotification(notification);
    }

    /**
     * 알림 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Notification> findNotification(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 알림 리스트 조회");
        return notificationRepository.findNotificationList(memberUuid);
    }

    /**
     * 알림 읽음 여부 변경
     * @param notificationList
     */
    @Transactional
    public void changeIsReadNotification(List<Notification> notificationList) {

        log.info("[Service] 알림 존재 확인");
        for (Notification notification: notificationList) {

            checkService.checkNotificationExists(notification);
        }

        log.info("[Service] 알림 읽음 여부 변경");
        notificationRepository.changeIsReadNotification(notificationList);
    }

    /**
     * Dto -> Entity 변환
     * @param reqDto
     * @param toMemberUuid
     * @return
     */
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
}
