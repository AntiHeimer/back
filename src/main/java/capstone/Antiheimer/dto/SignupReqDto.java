package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupReqDto {

    private String id;
    private String pw;
    private String name;
}
