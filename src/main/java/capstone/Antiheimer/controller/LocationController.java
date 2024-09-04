package capstone.Antiheimer.controller;

import capstone.Antiheimer.domain.Location;
import capstone.Antiheimer.dto.LocationReqDto;
import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.dto.RecentLocationResDto;
import capstone.Antiheimer.exception.DuplicateLocationException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NotExistLocationException;
import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
                                     @RequestBody String request) {

        log.info("권한 확인");
        if (!auth.equals(authKey)) {

            log.warn("권한이 없습니다.");
            return new NormalResDto("401", "권한 없음");
        }

        try{
            String decryptedRequest = aesService.decryptAES(request);
            LocationReqDto reqDto = objectMapper.readValue(decryptedRequest, LocationReqDto.class);

            log.info("위치 데이터 저장 시작");
            locationService.insertLocation(request, reqDto);

            return new NormalResDto("200", "위치 정보 저장 성공");
        } catch (DuplicateLocationException e) {

            return new NormalResDto("407", "중복된 위치 정보");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 최근 위치 정보 조회
     * @param request
     * @return
     */
    @GetMapping("/recent/location")
    public RecentLocationResDto recentLocation(@RequestParam("memberUuid") String request) {

        try {
            log.info("최근 위치 정보 조회 시작");

            String memberUuid = aesService.decryptAES(request);
            Location location = locationService.recentLocation(memberUuid);
            String decryptedRequest = aesService.decryptAES(location.getEncryptedLocation());
            LocationReqDto resDto = objectMapper.readValue(decryptedRequest, LocationReqDto.class);

            result = new RecentLocationResDto("200", "위치 정보 조회 성공", resDto.getFormattedDate(), resDto.getLocation());

            return result;
        } catch (NotExistException e) {

            result = new RecentLocationResDto("408", "존재하지 않는 회원", null, null);

            return result;
        } catch (NotExistLocationException e) {

            result = new RecentLocationResDto("408", "존재하지 않는 위치 정보", null, null);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
