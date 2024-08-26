package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.dto.SaveSleepReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SleepDataController {

    @Autowired
    private final hea;

    /**
     * 수면데이터 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/sleep")
    public NormalResDto saveSleep(@RequestBody SaveSleepReqDto request) {

        try {

            log.info("Sleep데이터 저장 시작");
            h.insertSleep(request);

            return null;
        } catch (Exception e) {

            return null;
        }
    }
}
