package capstone.Antiheimer.feature.relation.controller;

import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianResDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardResDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import capstone.Antiheimer.feature.relation.service.RelationService;
import capstone.Antiheimer.util.dto.NormalResDto;
import capstone.Antiheimer.util.encrypt.AesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RelationController {

    private final RelationService relationService;
    private final AesService aesService;

    /**
     * 보호자 등록
     *
     * @param request
     * @return
     */
    @PostMapping("/save-relation/guardian")
    public NormalResDto saveGuardian(@RequestBody SaveGuardianReqDto request) {

        try {
            log.info("보호자 등록 시작");
            relationService.saveGuardian(request);

            log.info("보호자 등록 성공");
            return new NormalResDto("200", "보호자 등록 성공");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 피보호자 등록
     *
     * @param request
     * @return
     */
    @PostMapping("/save-relation/ward")
    public NormalResDto saveWard(@RequestBody SaveWardReqDto request) {

        try {
            log.info("피보호자 등록 시작");
            relationService.saveWard(request);

            log.info("피보호자 등록 성공");
            return new NormalResDto("200", "피보호자 등록 성공");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    /**
     * 보호자 조회
     *
     * @param memberUuid
     * @return
     */
    @GetMapping("/info-relation/ward/{memberUuid}")
    public InfoWardResDto infoWard(@PathVariable String memberUuid) {

        try {
            log.info("uuid 복호화");
            String decryptedMemberUuid = aesService.decryptAES(memberUuid);

            log.info("피보호자 정보 조회 시작");
            List<InfoWardDto> infoWardList = relationService.infoWard(decryptedMemberUuid);

            log.info("피보호자 정보 조회 성공");
            return new InfoWardResDto("200", "피보호자 정보 조회 성공", infoWardList);
        } catch (NotExistException e) {

            return new InfoWardResDto("408", "존재하지 않는 회원", null);
        }
    }

    /**
     * 피보호자 조회
     *
     * @param memberUuid
     * @return
     */
    @GetMapping("/info-relation/guardian/{memberUuid}")
    public InfoGuardianResDto infoGuardian(@PathVariable String memberUuid) {

        try {
            log.info("uuid 복호화");
            String decryptedMemberUuid = aesService.decryptAES(memberUuid);

            log.info("피보호자 정보 조회 시작");
            List<InfoGuardianDto> infoGuardianList = relationService.infoGuardian(decryptedMemberUuid);

            log.info("피보호자 정보 조회 성공");
            return new InfoGuardianResDto("200", "보호자 정보 조회 성공", infoGuardianList);
        } catch (NotExistException e) {

            return new InfoGuardianResDto("408", "존재하지 않는 회원", null);
        }
    }

}
