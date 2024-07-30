package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @Column(name = "location_id")
    private String uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    @NotNull
    private LocalDateTime dateTime;
}
