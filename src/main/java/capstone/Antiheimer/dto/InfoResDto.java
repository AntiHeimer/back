package capstone.Antiheimer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class InfoResDto {

    private String statusCode;
    private String message;
    private String uuid;
    private String id;
    private String name;

    public InfoResDto(String statusCode, String message, String uuid, String id, String name) {
        this.statusCode = statusCode;
        this.message = message;
        this.uuid = uuid;
        this.id = id;
        this.name = name;
    }
}
