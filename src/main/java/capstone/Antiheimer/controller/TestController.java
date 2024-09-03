package capstone.Antiheimer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> HelloWorld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken() {
        return new ResponseEntity<>("토큰 확인 완료", HttpStatus.OK);
    }
}
