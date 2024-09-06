package capstone.Antiheimer.feature.health_data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Active {

    @Id
    @Column(name = "active_uuid")
    private String uuid;

    @NotNull
    private LocalDate date; // date 단위

    @NotNull
    private int activeEnergyBurned;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthdata_id")
    private HealthData healthData;
}
