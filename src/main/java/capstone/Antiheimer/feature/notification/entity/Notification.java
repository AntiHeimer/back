package capstone.Antiheimer.feature.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @NotNull
    private String memberUuid;

    private String fromMemberUuid;

    private String fromMemberName;

    public void setUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
