package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ActiveReqDto {

    private String memberUuid;
    private LocalDate date;
    private int activeEnergyBurned;
    private int activeEnergyBurnedGoal;
    private int appleExerciseTime;
    private int appleExerciseTimeGoal;
    private int appleStandHours;
    private int appleStandHoursGoal;
}
