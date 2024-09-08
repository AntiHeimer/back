package capstone.Antiheimer.feature.relation.service;

import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveGuardianReqDto;
import capstone.Antiheimer.feature.relation.dto.save.SaveWardReqDto;
import capstone.Antiheimer.feature.relation.entity.Relation;
import capstone.Antiheimer.feature.relation.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    /**
     * 보호자 등록
     *
     * @param reqDto
     */
    @Transactional
    public void saveGuardian(SaveGuardianReqDto reqDto) {

        String wardUuid = memberRepository.findOneById(reqDto.getGuardianId()).getUuid();
        String guardianUuid = reqDto.getWardUuid();

        if (isExist(wardUuid) && isExist(guardianUuid)) {

            Relation relation = convertToEntity(wardUuid, guardianUuid);
            relationRepository.save(relation);
        }
    }

    /**
     * 피보호자 등록
     *
     * @param reqDto
     */
    @Transactional
    public void saveWard(SaveWardReqDto reqDto) {

        String wardUuid = memberRepository.findOneById(reqDto.getWardId()).getUuid();
        String guardianUuid = reqDto.getGuardianUuid();

        if (isExist(wardUuid) && isExist(guardianUuid)) {

            Relation relation = convertToEntity(wardUuid, guardianUuid);
            relationRepository.save(relation);
        }
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
        relation.setGuardianId(guardianUuid);
        relation.setWardId(wardUuid);

        return relation;
    }

    /**
     * 회원 존재 확인
     *
     * @param uuid
     * @return
     */
    public boolean isExist(String uuid) {

        if (memberRepository.findOneByUuid(uuid) == null) {

            throw new NotExistException();
        } else {
            return true;
        }
    }
}
