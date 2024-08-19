package capstone.Antiheimer.domain;

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

    @NotNull
    private int activeEnergyBurnedGoal;

    @NotNull
    private int appleExerciseTime;

    @NotNull
    private int appleExerciseTimeGoal;

    @NotNull
    private int appleStandHours;

    @NotNull
    private int appleStandHoursGoal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberdata_id")
    private MemberData memberData;
}
