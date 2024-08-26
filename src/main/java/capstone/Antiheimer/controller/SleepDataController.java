package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.dto.SaveMoveReqDto;
import capstone.Antiheimer.dto.SaveSleepReqDto;
import capstone.Antiheimer.service.SleepDataService;
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
    private final SleepDataService sleepDataService;

    /**
     * 수면데이터 저장
     * @param request
     * @return NormalResDto
     */
    @PostMapping("/save/sleep")
    public NormalResDto saveSleep(@RequestBody SaveSleepReqDto request) {

        try {

            log.info("Sleep데이터 저장 시작");
            sleepDataService.insertSleep(request);
        }
    }
}
