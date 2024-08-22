package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.*;
import capstone.Antiheimer.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HealthDataRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    public void saveActive(SaveActiveReqDto request) {

        Active active = new Active();

        List<ActiveVo> activeVoList = request.getActiveData();

        active.setUuid(UUID.randomUUID().toString());
        active.setDate(request.getDate());
        active.setActiveEnergyBurned(activeVoList.getFirst().getActiveEnergyBurned());

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());

        // MemberData에서 findMember와 uuid가 같고 date가 같은 객체 찾기
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), active.getDate());

        // findMember의 해당하는 date가 없음
        if (findHealthData == null) {

            HealthData newHealthData = new HealthData();

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(active.getDate());
            newHealthData.setActive(active);
            active.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else {
            findHealthData.setActive(active);
            active.setHealthData(findHealthData);

            em.persist(findHealthData);
        }
        em.persist(active);
    }

    public void saveMove(SaveMoveReqDto request) {

        List<MoveVo> moveVoList = request.getMoveData();

        Move move = new Move();

        move.setUuid(UUID.randomUUID().toString());
        move.setDate(request.getDate());

        int sumValue = 0;

        for (MoveVo moveVo : moveVoList) {

            sumValue += moveVo.getValue();
        }

        move.setValue(sumValue);

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

        if (findHealthData == null) {

            HealthData newHealthData = new HealthData();

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(request.getDate());
            move.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else {

            move.setHealthData(findHealthData);

            em.persist(findHealthData);
        }

        em.persist(move);
    }

    public void saveWalk(SaveWalkReqDto request) {

        List<WalkVo> walkVoList = request.getWalkData();

        Walk walk = new Walk();

        walk.setUuid(UUID.randomUUID().toString());
        walk.setDate(request.getDate());

        int sumValue = 0;

        for (WalkVo walkVo : walkVoList) {

            sumValue += walkVo.getValue();
        }

        walk.setValue(sumValue);

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

        if (findHealthData == null) {

            HealthData newHealthData = new HealthData();

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(request.getDate());
            walk.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else {

            walk.setHealthData(findHealthData);

            em.persist(findHealthData);
        }

        em.persist(walk);
    }

    public void saveWeight(SaveWeightReqDto request) {

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        findMember.setWeight(request.getWeight());

        em.persist(findMember);
    }

    public HealthData findDataByMemberAndDate(String uuid, LocalDate date) {

        try {
            return em.createQuery("select h from HealthData h where h.date = :date and h.member.uuid = :uuid", HealthData.class)
                    .setParameter("uuid", uuid).setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

//    public List<Active> findActive(String uuid, LocalDate date) {
//
//        HealthData healthData = findDataByMemberAndDate(uuid, date);
//
//        try {
//            return em.createQuery("select a from Active a where a.healthData.uuid = :uuid", Active.class)
//                    .setParameter("uuid", healthData.getUuid())
//                    .getResultList();
//        } catch (NullPointerException e) {
//            return null;
//        }
//    }
//
//    public List<Move> findMove(String uuid, LocalDate date) {
//
//        HealthData healthData = findDataByMemberAndDate(uuid, date);
//
//        try {
//            return em.createQuery("select m from Move m where m.healthData.uuid = :uuid", Move.class)
//                    .setParameter("uuid", healthData.getUuid())
//                    .getResultList();
//        } catch (NullPointerException e) {
//            return null;
//        }
//    }
//
//    public List<Walk> findWalk(String uuid, LocalDate date) {
//
//        HealthData healthData = findDataByMemberAndDate(uuid, date);
//
//        try {
//            return em.createQuery("select w from Walk w where w.healthData.uuid = :uuid", Walk.class)
//                    .setParameter("uuid", healthData.getUuid())
//                    .getResultList();
//        } catch (NullPointerException e) {
//            return null;
//        }
//    }

    public boolean existActive(SaveActiveReqDto request) {

        try {
            em.createQuery("select a from Active a where a.healthData.member.uuid = :uuid and a.date = :date", Active.class)
                    .setParameter("uuid", request.getMemberUuid()).setParameter("date", request.getDate())
                    .getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existMove(SaveMoveReqDto request) {

        try {
            em.createQuery("select m from Move m where m.healthData.member.uuid = :uuid and m.date = :date", Move.class)
                    .setParameter("uuid", request.getMemberUuid()).setParameter("date", request.getDate())
                    .getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existWalk(SaveWalkReqDto request) {

        try {
            em.createQuery("select w from Walk w where w.healthData.member.uuid = :uuid and w.date = :date", Walk.class)
                    .setParameter("uuid", request.getMemberUuid()).setParameter("date", request.getDate())
                    .getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public List<HealthData> findDataByMember(String uuid) {

        return em.createQuery("select h from HealthData h where h.member.uuid = :uuid", HealthData.class)
                .setParameter("uuid", uuid)
                .getResultList();
    }
}
