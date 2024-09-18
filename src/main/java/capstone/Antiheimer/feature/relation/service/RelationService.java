package capstone.Antiheimer.feature.relation.service;

import capstone.Antiheimer.exception.DuplicateRelationException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.relation.dto.RequestRelationReqDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import capstone.Antiheimer.feature.relation.entity.Relation;
import capstone.Antiheimer.feature.relation.repository.RelationRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.tokens.ScalarToken;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    /**
     * 관계 임시 등록(active false 상태)
     *
     * @param reqDto
     */
    @Transactional
    public void saveRelation(RequestRelationReqDto reqDto) {

        if (reqDto.getRequestType().equals("guardian")) {

            try {
                memberRepository.findById(reqDto.getToMemberId());

                String guardianUuid = memberRepository.findOneById(reqDto.getToMemberId()).getUuid();
                String wardUuid = reqDto.getFromMemberUuid();

                if (isExist(wardUuid) && isExist(guardianUuid)) {

                    Relation relation = convertToEntity(wardUuid, guardianUuid);

                    if (relationRepository.isRelationExist(relation)) {

                        log.warn("이미 존재하는 관계");
                        throw new DuplicateRelationException();
                    }
                    relationRepository.saveRelation(relation);
                }
            } catch(EmptyResultDataAccessException e) {
                throw new NotExistException();
            }
        } else if (reqDto.getRequestType().equals("ward")) {

            try {
                memberRepository.findById(reqDto.getToMemberId());

                String wardUuid = memberRepository.findOneById(reqDto.getToMemberId()).getUuid();
                String guardianUuid = reqDto.getFromMemberUuid();

                if (isExist(wardUuid) && isExist(guardianUuid)) {

                    Relation relation = convertToEntity(wardUuid, guardianUuid);

                    if (relationRepository.isRelationExist(relation)) {

                        log.warn("이미 존재하는 관계");
                        throw new DuplicateRelationException();
                    }
                    relationRepository.saveRelation(relation);
                }
            } catch(EmptyResultDataAccessException e) {
                    throw new NotExistException();
                }
        }
    }

    /**
     * 보호자 등록(active true 변경)
     *
     * @param reqDto
     */
    @Transactional
    public void saveGuardian(SaveGuardianReqDto reqDto) {

        String guardianUuid = memberRepository.findOneById(reqDto.getGuardianId()).getUuid();
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

        String wardUuid = memberRepository.findOneById(reqDto.getWardId()).getUuid();
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

        isExist(memberUuid);

        return relationRepository.infoGuardian(memberUuid);
    }

    /**
     * 피보호자 조회
     *
     * @param memberUuid
     * @return
     */
    public List<InfoWardDto> infoWard(String memberUuid) {

        isExist(memberUuid);

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

    /**
     * 회원 존재 확인
     *
     * @param uuid
     * @return
     */
    public boolean isExist(String uuid) {

        if (memberRepository.findOneById(uuid) == null) {

            throw new NotExistException();
        } else {
            return true;
        }
    }
}
