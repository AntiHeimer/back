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
public class Sleep {

    @Id
    @Column(name = "sleep_id")
    private String uuid;

    @NotNull
    private LocalDate date; // 날짜 단위로 저장

    private int rem;

    private int core;

    private int deep;

    private int sleepTime;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthdata_id")
    HealthData healthData;
}
