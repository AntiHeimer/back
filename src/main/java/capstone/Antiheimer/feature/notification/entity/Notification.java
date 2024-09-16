package capstone.Antiheimer.feature.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @Column(name = "notifiation_id")
    private String uuid;

    @NotNull
    private String type;

    @NotNull
    private boolean isRead;

    @NotNull
    private String memberUuid;

    private String fromMemberUuid;

    private String fromMemberName;

    public void setUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
