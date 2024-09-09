package capstone.Antiheimer.feature.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Entity
public class Notification {

    @Id
    @Column(name = "notifiation_id")
    private String uuid;

    @NotNull
    private NotificationType type;

    @NotNull
    private boolean isRead;

    @NotNull
    private String memberUuid;

    private String fromMemberId;

    public enum NotificationType {
        GUARDIAN, WARD
    }
}
