package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SaveWeightReqDto {

    private String memberUuid;
    private double weight;
}
