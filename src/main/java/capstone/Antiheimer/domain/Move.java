package capstone.Antiheimer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Move {

    @Id
    @Column(name = "move_id")
    private String uuid;

    @NotNull
    private LocalDate date;

    @NotNull
    private double value;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthdata_id")
    HealthData healthData;
}
