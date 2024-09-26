package capstone.Antiheimer.feature.notification.repository;

import capstone.Antiheimer.feature.notification.entity.Notification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    private final EntityManager em;

    /**
     * 알림 저장
     * @param notification
     */
    public void saveNotification(Notification notification) {

        em.persist(notification);
    }

    /**
     * 알림 삭제
     * @param notification
     */
    public void deleteNotification(Notification notification) {

        em.remove(notification);
    }

    /**
     * 알림 조회
     * @param notificationUuid
     * @return
     */
    public Notification findNotification(String notificationUuid) {

        return em.find(Notification.class, notificationUuid);
    }

    /**
     * 알림 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Notification> findNotificationList(String memberUuid) {

        return em.createQuery("SELECT n FROM Notification n WHERE n.memberUuid = :memberUuid", Notification.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

    /**
     * 알림 읽음 여부 변경
     * @param notificationList
     */
    public void changeIsReadNotification(List<Notification> notificationList) {

        for (Notification notification : notificationList) {

            findNotification(notification.getUuid()).setRead(true);
            em.persist(notification);
        }
    }
}
