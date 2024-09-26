package capstone.Antiheimer.feature.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
public class Notification {

    @Id
    @Column(name = "notification_id")
    private String uuid;

    private String fromMemberUuid;
    private String fromMemberName;
    private String toMemberUuid;
    private LocalDateTime datetime;

    private Notification create(String fromMemberUuid, String fromMemberName, String toMemberUuid, LocalDateTime datetime) {

        Notification notification = Notification.builder()
                .uuid(UUID.randomUUID().toString())
                .fromMemberUuid(fromMemberUuid)
                .fromMemberName(fromMemberName)
                .toMemberUuid(toMemberUuid)
                .datetime(datetime).build();

        return notification;
    }
}
