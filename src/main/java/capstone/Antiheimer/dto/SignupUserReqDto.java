package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupUserReqDto {

    private String id;
    private String pw;
    private String name;

    public SignupUserReqDto(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }
}
