package capstone.Antiheimer.feature.relation.repository;

import capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto;
import capstone.Antiheimer.feature.relation.dto.info.InfoWardDto;
import capstone.Antiheimer.feature.relation.entity.Relation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RelationRepository {


    private final EntityManager em;

    public void save(Relation relation) {

        em.persist(relation);
    }

    public List<InfoWardDto> infoWard(String memberUuid) {

        return em.createQuery("SELECT new capstone.Antiheimer.feature.relation.dto.info.InfoWardDto(m.uuid, m.id, m.name) FROM Member m JOIN Relation r ON m.uuid = r.wardId WHERE r.guardianId = :memberUuid", InfoWardDto.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

    /**
     *
     * @param memberUuid
     * @return
     */
    public List<InfoGuardianDto> infoGuardian(String memberUuid) {

        return em.createQuery("SELECT new capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto(m.uuid, m.id, m.name) FROM Member m JOIN Relation r ON m.uuid = r.guardianId WHERE r.wardId = :memberUuid", InfoGuardianDto.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

}
