package capstone.Antiheimer.dto;

import capstone.Antiheimer.domain.Active;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindActiveResDto {

    private String statusCode;
    private String message;
    private List<Active> activeList;

    public FindActiveResDto(String statusCode, String message, List<Active> activeList) {
        this.statusCode = statusCode;
        this.message = message;
        this.activeList = activeList;
    }
}
