package capstone.Antiheimer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Move {

    @Id
    @Column(name = "move_id")
    private String uuid;

    @NotNull
    private LocalDateTime startDateTime; // datetime 단위

    @NotNull
    private LocalDateTime endDateTime; // datetime 단위

    @NotNull
    private double value;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthdata_id")
    HealthData healthData;
}
