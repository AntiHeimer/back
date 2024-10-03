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

    private int diagnosisScore;
    private List<Active> activeList;
    private List<Sleep> sleepList;
    private List<Walk> walkList;
    private List<Move> moveList;

    public AiSendDto(int diagnosisScore, List<Active> activeList, List<Sleep> sleepList, List<Walk> walkList, List<Move> moveList) {
        this.diagnosisScore = diagnosisScore;
        this.activeList = activeList;
        this.sleepList = sleepList;
        this.walkList = walkList;
        this.moveList = moveList;
    }
}
