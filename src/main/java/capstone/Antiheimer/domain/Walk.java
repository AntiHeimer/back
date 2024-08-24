package capstone.Antiheimer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Walk {

    @Id
    @Column(name = "walk_id")
    private String uuid;

    @NotNull
    private LocalDate date;

    @NotNull
    private int value;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthdata_id")
    private HealthData healthData;
}
