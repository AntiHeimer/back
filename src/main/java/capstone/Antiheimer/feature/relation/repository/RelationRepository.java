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

    public void saveRelation(Relation relation) {

        em.persist(relation);
    }

    public void saveGuardian(Relation relation) {

        relation.setActive(true);
        em.persist(relation);
    }

    public void saveWard(Relation relation) {

        relation.setActive(true);
        em.persist(relation);
    }

    public Relation findRelation(String guardianUuid, String wardUuid) {

        List<Relation> findRelation = em.createQuery("SELECT r FROM Relation r WHERE (r.guardianUuid = :guardianUuid AND r.wardUuid = :wardUuid)", Relation.class)
                .setParameter("guardianUuid", guardianUuid).setParameter("wardUuid", wardUuid)
                .getResultList();

        return findRelation.isEmpty() ? null : findRelation.get(0);
    }

    public boolean isRelationExist(Relation relation) {

        List<Relation> findRelation = em.createQuery("SELECT r FROM Relation r WHERE (r.guardianUuid = :guardianUuid AND r.wardUuid = :wardUuid AND r.active = true)", Relation.class)
                .setParameter("guardianUuid", relation.getGuardianUuid()).setParameter("wardUuid", relation.getWardUuid())
                .getResultList();

        return !findRelation.isEmpty();
    }

    public List<InfoWardDto> infoWard(String memberUuid) {

        return em.createQuery("SELECT new capstone.Antiheimer.feature.relation.dto.info.InfoWardDto(m.uuid, m.id, m.name) FROM Member m JOIN Relation r ON m.uuid = r.wardUuid WHERE r.guardianUuid = :memberUuid", InfoWardDto.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

    /**
     *
     * @param memberUuid
     * @return
     */
    public List<InfoGuardianDto> infoGuardian(String memberUuid) {

        return em.createQuery("SELECT new capstone.Antiheimer.feature.relation.dto.info.InfoGuardianDto(m.uuid, m.id, m.name) FROM Member m JOIN Relation r ON m.uuid = r.guardianUuid WHERE r.wardUuid = :memberUuid", InfoGuardianDto.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

}
