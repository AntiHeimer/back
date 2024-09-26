package capstone.Antiheimer.feature.dementia_result.dto;

import capstone.Antiheimer.feature.dementia_result.entity.Result;
import capstone.Antiheimer.util.dto.NormalResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ResultResDto extends NormalResDto {

    private final List<Result> resultList;

    public ResultResDto(String statusCode, String message, List<Result> resultList) {
        super(statusCode, message);
        this.resultList = resultList;
    }
}