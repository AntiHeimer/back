package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.LocationReqDto;
import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    @Autowired
    private final LocationService locationService;

    @PostMapping("/save/location")
    public NormalResDto saveLocation(@RequestBody LocationReqDto request) {

        try{
            log.info("위치 데이터 저장 시작");
            locationService.insertLocation(request);
        }
    }
}
