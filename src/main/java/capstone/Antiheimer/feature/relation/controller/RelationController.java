package capstone.Antiheimer.feature.relation.controller;

import capstone.Antiheimer.feature.relation.dto.RequestRelationReqDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianResDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardResDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import capstone.Antiheimer.feature.notification.service.NotificationService;
import capstone.Antiheimer.feature.relation.service.RelationService;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RelationController {

    private final RelationService relationService;
    private final NotificationService notificationService;
    private final AesService aesService;

    /**
     * 관계 요청 알림
     * @param reqDto
     * @return
     */
    @PostMapping("/request-relation")
    public ResponseEntity<NormalResDto> requestRelation(@RequestBody RequestRelationReqDto reqDto) {

        // 관계 저장(비활성화)
        relationService.saveRelation(reqDto);

        log.info("[Controller] [Controller] 관계 요청 알림 저장 시작");
        notificationService.saveRequestNotification(reqDto);

        log.info("[Controller] [Controller] 관계 요청 알림 저장 성공");
        return new ResponseEntity<>(new NormalResDto("200", "관계 요청 성공"), HttpStatus.OK);
    }

    /**
     * 보호자 등록
     * @param reqDto
     * @return
     */
    @PostMapping("/save-relation/guardian")
    public ResponseEntity<NormalResDto> saveGuardian(@RequestBody SaveGuardianReqDto reqDto) {

        log.info("[Controller] 보호자 등록 시작");
        relationService.saveGuardian(reqDto);

        log.info("[Controller] 보호자 등록 알림 저장");
        notificationService.saveGuardianNotification(reqDto);
        log.info("[Controller] 요청 알림 삭제");
        notificationService.deleteNotification(reqDto.getNotificationUuid());

        log.info("[Controller] 보호자 등록 성공");
        return new ResponseEntity<>(new NormalResDto("200", "보호자 등록 성공"), HttpStatus.OK);
    }

    /**
     * 피보호자 등록
     * @param reqDto
     * @return
     */
    @PostMapping("/save-relation/ward")
    public ResponseEntity<NormalResDto> saveWard(@RequestBody SaveWardReqDto reqDto) {

        log.info("[Controller] 피보호자 등록 시작");
        relationService.saveWard(reqDto);

        log.info("[Controller] 피보호자 등록 알림 저장");
        notificationService.saveWardNotification(reqDto);
        log.info("[Controller] 요청 알림 삭제");
        notificationService.deleteNotification(reqDto.getNotificationUuid());

        log.info("[Controller] 피보호자 등록 성공");
        return new ResponseEntity<>(new NormalResDto("200", "피보호자 등록 성공"), HttpStatus.OK);
    }

    /**
     * 보호자 조회
     * @param memberUuid
     * @return
     */
    @GetMapping("/info-relation/ward")
    public ResponseEntity<InfoWardResDto> infoWard(@RequestParam String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedMemberUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 피보호자 정보 조회 시작");
        List<InfoWardDto> infoWardList = relationService.infoWard(decryptedMemberUuid);

        log.info("[Controller] 피보호자 정보 조회 성공");
        return new ResponseEntity<>(new InfoWardResDto("200", "피보호자 정보 조회 성공", infoWardList), HttpStatus.OK);
    }

    /**
     * 피보호자 조회
     * @param memberUuid
     * @return
     */
    @GetMapping("/info-relation/guardian")
    public ResponseEntity<InfoGuardianResDto> infoGuardian(@RequestParam String memberUuid) throws UnsupportedEncodingException {

        log.info("[Controller] 디코딩 및 AES 복호화");
        // URL 디코딩
        String decodedUuid = URLDecoder.decode(memberUuid, StandardCharsets.UTF_8.name());
        // 공백을 +로 변환
        String plusEncodedString = decodedUuid.replace(" ", "+");
        // uuid 복호화
        String decryptedMemberUuid = aesService.decryptAES(plusEncodedString);

        log.info("[Controller] 피보호자 정보 조회 시작");
        List<InfoGuardianDto> infoGuardianList = relationService.infoGuardian(decryptedMemberUuid);

        log.info("[Controller] 피보호자 정보 조회 성공");
        return new ResponseEntity<>(new InfoGuardianResDto("200", "보호자 정보 조회 성공", infoGuardianList), HttpStatus.OK);
    }
}
