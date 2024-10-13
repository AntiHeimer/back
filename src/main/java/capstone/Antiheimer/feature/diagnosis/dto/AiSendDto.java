package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.health_data.entity.Active;
import capstone.Antiheimer.feature.health_data.entity.Move;
import capstone.Antiheimer.feature.health_data.entity.Sleep;
import capstone.Antiheimer.feature.health_data.entity.Walk;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiSendDto {

    private int score;
    private List<Integer> active_energy_burned;
    private List<Double> move;
    private List<Integer> walk;
    private List<Integer> deep;
    private List<Integer> rem;
    private List<Integer> awake;
    private List<Integer> sleep_time;

    public AiSendDto(int diagnosisScore, List<Double> move, List<Integer> walk, List<Integer> active_energy_burned,
                     List<Integer> deep, List<Integer> rem, List<Integer> awake, List<Integer> sleep_time) {
        this.score = diagnosisScore;
        this.active_energy_burned = active_energy_burned;
        this.move = move;
        this.walk = walk;
        this.deep = deep;
        this.rem = rem;
        this.awake = awake;
        this.sleep_time = sleep_time;
    }
}
