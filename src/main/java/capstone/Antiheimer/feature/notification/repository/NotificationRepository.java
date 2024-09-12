package capstone.Antiheimer.feature.notification.repository;

import capstone.Antiheimer.feature.notification.dto.NotificationDto;
import capstone.Antiheimer.feature.notification.entity.Notification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    private final EntityManager em;

    public void saveNotification(Notification notification) {

        em.persist(notification);
    }

    public void deleteNotification(Notification notification) {

        em.remove(notification);
    }

    public Notification findNotificationByUuid(String notificationUuid) {

        return em.find(Notification.class, notificationUuid);
    }

    public List<NotificationDto> findNotificationListByUuid(String memberUuid) {

        return em.createQuery("SELECT new capstone.Antiheimer.feature.notification.dto.NotificationDto(n.uuid, n.fromMemberUuid, n.fromMemberName, n.isRead, n.type) FROM Notification n WHERE n.memberUuid = :memberUuid", NotificationDto.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

    public void changeIsReadNotification(List<NotificationDto> notificationDtoList) {

        for (NotificationDto notificationDto : notificationDtoList) {

            Notification notification = findNotificationByUuid(notificationDto.getNotificationUuid());

            notification.setRead(true);
            em.persist(notification);
        }
    }
}
