package capstone.Antiheimer.util.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> HelloWorld() {

        log.trace("TRACE LEVEL 테스트");
        log.debug("DEBUG LEVEL 테스트");
        log.info("INFO LEVEL 테스트");
        log.warn("WARN LEVEL 테스트");
        log.error("ERROR LEVEL 테스트");
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
