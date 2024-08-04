package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final AesService aesService;

    @Value("아직몰라")
    private String authKey;

    private NormalResDto result;
}
