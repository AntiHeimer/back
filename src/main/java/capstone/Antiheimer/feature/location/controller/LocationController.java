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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    @Autowired
    private final LocationService locationService;
    @Autowired
    private final AesService aesService;
    @Autowired
    private final ObjectMapper objectMapper;

    @Value("${auth.key}")
    private String authKey;

    /**
     * 위치 정보 저장
     * @param auth
     * @param request
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<NormalResDto> saveLocation(@RequestHeader("auth") String auth,
                                                    @RequestBody String request) throws JsonProcessingException {

        log.info("[Controller] 권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다");
            return new ResponseEntity<>(new NormalResDto("401", "권한 없음"), HttpStatus.UNAUTHORIZED);
        }

        log.info("[Controller] AES 복호화");
        String decryptedRequest = aesService.decryptAES(request);
        LocationReqDto reqDto = objectMapper.readValue(decryptedRequest, LocationReqDto.class);

        log.info("[Controller] 위치 데이터 저장 시작");
        locationService.saveLocation(request, reqDto);

        log.info("[Controller] 위치 데이터 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "위치 데이터 저장 성공"), HttpStatus.OK);
    }

    /**
     * 최근 위치 정보 조회
     * @param request
     * @return
     */
    @GetMapping("/recent")
    public ResponseEntity<RecentLocationResDto> recentLocation(@RequestParam("memberUuid") String request) throws UnsupportedEncodingException, JsonProcessingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(request, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        String memberUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 최근 위치 정보 조회 시작");
        Location location = locationService.recentLocation(memberUuid);
        String decryptedRequest = aesService.decryptAES(location.getEncryptedLocation());
        LocationReqDto resDto = objectMapper.readValue(decryptedRequest, LocationReqDto.class);

        log.info("[Controller] 최근 위치 정보 조회 성공");
        return new ResponseEntity<>(new RecentLocationResDto("200", "위치 정보 조회 성공", resDto.getFormattedDate(), resDto.getLocation()), HttpStatus.OK);
    }
}
