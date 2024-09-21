package capstone.Antiheimer.feature.location.controller;

import capstone.Antiheimer.feature.location.service.LocationService;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.feature.location.dto.RecentLocationResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    @Autowired
    private final LocationService locationService;
    @Autowired
    private final AesService aesService;
    @Autowired
    private final ObjectMapper objectMapper;

    @Value("${auth.key}")
    private String authKey;

    RecentLocationResDto result;

    /**
     * 위치 정보 저장
     * @param auth
     * @param request
     * @return
     */
    @PostMapping("/save/location")
    public NormalResDto saveLocation(@RequestHeader("auth") String auth,
                                     @RequestBody String request) throws JsonProcessingException {

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다.");
            return new NormalResDto("401", "권한 없음");
        }

        String decryptedRequest = aesService.decryptAES(request);
        LocationReqDto reqDto = objectMapper.readValue(decryptedRequest, LocationReqDto.class);

        log.info("위치 데이터 저장 시작");
        locationService.insertLocation(request, reqDto);

        return new NormalResDto("200", "위치 정보 저장 성공");
    }

    /**
     * 최근 위치 정보 조회
     * @param request
     * @return
     */
    @GetMapping("/recent/location")
    public RecentLocationResDto recentLocation(@RequestParam("memberUuid") String request) throws UnsupportedEncodingException, JsonProcessingException {

        log.info("최근 위치 정보 조회 시작");

        // URL 디코딩
        String decodedUuid = URLDecoder.decode(request, StandardCharsets.UTF_8.name());

        System.out.println("decodedUuid = " + decodedUuid);
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");

        System.out.println("plusEncodedString = " + plusEncodedString);

        String memberUuid = aesService.decryptAES(plusEncodedString);
        Location location = locationService.recentLocation(memberUuid);
        String decryptedRequest = aesService.decryptAES(location.getEncryptedLocation());
        LocationReqDto resDto = objectMapper.readValue(decryptedRequest, LocationReqDto.class);

        return new RecentLocationResDto("200", "위치 정보 조회 성공", resDto.getFormattedDate(), resDto.getLocation());
    }
}
