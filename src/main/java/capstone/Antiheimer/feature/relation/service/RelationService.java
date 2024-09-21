package capstone.Antiheimer.feature.relation.service;

import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.util.CheckService;
import capstone.Antiheimer.feature.relation.dto.RequestRelationReqDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import capstone.Antiheimer.feature.relation.entity.Relation;
import capstone.Antiheimer.feature.relation.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final CheckService checkService;

    /**
     * 관계 임시 등록(active false 상태)
     *
     * @param reqDto
     */
    @Transactional
    public void saveRelation(RequestRelationReqDto reqDto) {

        String guardianUuid = null;
        String wardUuid = null;

        try {
            if (reqDto.getRequestType().equals("guardian")) {

                memberRepository.findById(reqDto.getToMemberId());

                guardianUuid = memberRepository.findOneById(reqDto.getToMemberId()).getUuid();
                wardUuid = reqDto.getFromMemberUuid();
            } else if (reqDto.getRequestType().equals("ward")) {

                memberRepository.findById(reqDto.getToMemberId());

                wardUuid = memberRepository.findOneById(reqDto.getToMemberId()).getUuid();
                guardianUuid = reqDto.getFromMemberUuid();
            }
        } catch(EmptyResultDataAccessException e){

            throw new NotExistMemberException();
        }

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(wardUuid);
        checkService.checkMemberExists(guardianUuid);

        Relation relation = convertToEntity(wardUuid, guardianUuid);

        log.info("[Service] 관계 존재 확인");
        checkService.checkDuplicateRelation(relation);

        log.info("[Service] 관계 저장");
        relationRepository.saveRelation(relation);
    }

    /**
     * 보호자 등록(active true 변경)
     *
     * @param reqDto
     */
    @Transactional
    public void saveGuardian(SaveGuardianReqDto reqDto) {

        String guardianUuid = memberRepository.findOneByUuid(reqDto.getGuardianUuid()).getUuid();
        String wardUuid = reqDto.getWardUuid();

        Relation relation = relationRepository.findRelation(guardianUuid, wardUuid);
        relationRepository.saveGuardian(relation);
    }

    /**
     * 피보호자 등록(active true 변경)
     *
     * @param reqDto
     */
    @Transactional
    public void saveWard(SaveWardReqDto reqDto) {

        String wardUuid = memberRepository.findOneByUuid(reqDto.getWardUuid()).getUuid();
        String guardianUuid = reqDto.getGuardianUuid();

        Relation relation = relationRepository.findRelation(guardianUuid, wardUuid);
        relationRepository.saveWard(relation);
    }

    /**
     * 보호자 조회
     *
     * @param memberUuid
     * @return
     */
    public List<InfoGuardianDto> infoGuardian(String memberUuid) {

        checkService.checkMemberExists(memberUuid);

        return relationRepository.infoGuardian(memberUuid);
    }

    /**
     * 피보호자 조회
     *
     * @param memberUuid
     * @return
     */
    public List<InfoWardDto> infoWard(String memberUuid) {

        checkService.checkMemberExists(memberUuid);

        return relationRepository.infoWard(memberUuid);
    }

    /**
     * Dto -> Entity 변환
     *
     * @param wardUuid
     * @param guardianUuid
     * @return
     */
    public Relation convertToEntity(String wardUuid, String guardianUuid) {

        Relation relation = new Relation();

        relation.setUuid();
        relation.setGuardianUuid(guardianUuid);
        relation.setWardUuid(wardUuid);
        relation.setActive(false);

        return relation;
    }
}
