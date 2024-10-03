package capstone.Antiheimer.feature.diagnosis.dto;

import capstone.Antiheimer.feature.diagnosis.entity.Result;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DementiaResultListResDto extends NormalResDto {

    private final List<Result> resultList;

    public DementiaResultListResDto(String statusCode, String message, List<Result> resultList) {
        super(statusCode, message);
        this.resultList = resultList;
    }
}