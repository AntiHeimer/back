package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Move;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindMoveResDto {

    private String statusCode;
    private String message;
    private List<Move> moveList;

    public FindMoveResDto(String statusCode, String message, List<Move> moveList) {
        this.statusCode = statusCode;
        this.message = message;
        this.moveList = moveList;
    }
}
