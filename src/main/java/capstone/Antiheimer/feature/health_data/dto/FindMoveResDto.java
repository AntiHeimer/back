package capstone.Antiheimer.feature.health_data.dto;

import capstone.Antiheimer.feature.health_data.entity.Move;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FindMoveResDto extends NormalResDto {

    private final List<Move> moveList;

    public FindMoveResDto(String statusCode, String message, List<Move> moveList) {
        super(statusCode, message);
        this.moveList = moveList;
    }
}
