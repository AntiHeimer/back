package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.LocationReqDto;
import capstone.Antiheimer.dto.NormalResDto;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.exception.DuplicateHealthDataException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.service.AesService;
import capstone.Antiheimer.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 위치 정보");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
