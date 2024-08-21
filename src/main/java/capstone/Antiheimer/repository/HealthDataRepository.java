package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.*;
import capstone.Antiheimer.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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

        for (ActiveVo activeVo : activeVoList) {

            active.setUuid(UUID.randomUUID().toString());
            active.setDate(request.getDate());
            active.setActiveEnergyBurned(activeVo.getActiveEnergyBurned());
            active.setActiveEnergyBurnedGoal(activeVo.getActiveEnergyBurnedGoal());
            active.setAppleExerciseTime(activeVo.getAppleExerciseTime());
            active.setAppleExerciseTimeGoal(activeVo.getAppleExerciseTimeGoal());
            active.setAppleStandHours(activeVo.getAppleStandHours());
            active.setAppleStandHoursGoal(activeVo.getAppleStandHoursGoal());
        }

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

        List<Move> moveList = new ArrayList<>();

        for (MoveVo moveVo : moveVoList) {

            Move move = new Move();

            move.setUuid(UUID.randomUUID().toString());
            move.setStartDateTime(moveVo.getStartDateTime());
            move.setEndDateTime(moveVo.getEndDateTime());
            move.setValue(moveVo.getValue());

            moveList.add(move);

            Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());

            HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

            if (findHealthData == null) {

                HealthData newHealthData = new HealthData();

                newHealthData.setUuid(UUID.randomUUID().toString());
                newHealthData.setMember(findMember);
                newHealthData.setDate(request.getDate());
                newHealthData.setMoveList(moveList);
                move.setHealthData(newHealthData);

                em.persist(newHealthData);
            } else {
                findHealthData.setMoveList(moveList);
                move.setHealthData(findHealthData);

                em.persist(findHealthData);
            }

            em.persist(move);
        }
    }

    public void saveWalk(SaveWalkReqDto request) {

        List<WalkVo> walkVoList = request.getWalkData();

        List<Walk> walkList = new ArrayList<>();

        for (WalkVo walkVo : walkVoList) {

            Walk walk = new Walk();

            walk.setUuid(UUID.randomUUID().toString());
            walk.setStartDateTime(walkVo.getStartDateTime());
            walk.setEndDateTime(walkVo.getEndDateTime());
            walk.setValue(walkVo.getValue());

            walkList.add(walk);

            Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());

            HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

            if (findHealthData == null) {

                HealthData newHealthData = new HealthData();

                newHealthData.setUuid(UUID.randomUUID().toString());
                newHealthData.setMember(findMember);
                newHealthData.setDate(request.getDate());
                newHealthData.setWalkList(walkList);
                walk.setHealthData(newHealthData);

                em.persist(newHealthData);
            } else {
                findHealthData.setWalkList(walkList);
                walk.setHealthData(findHealthData);

                em.persist(findHealthData);
            }

            em.persist(walk);
        }
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

    public List<Active> findActive(String uuid, LocalDate date) {

        HealthData healthData = findDataByMemberAndDate(uuid, date);

        try {
            return em.createQuery("select a from Active a where a.healthData.uuid = :uuid", Active.class)
                    .setParameter("uuid", healthData.getUuid())
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<Move> findMove(String uuid, LocalDate date) {

        HealthData healthData = findDataByMemberAndDate(uuid, date);

        try {
            return em.createQuery("select m from Move m where m.healthData.uuid = :uuid", Move.class)
                    .setParameter("uuid", healthData.getUuid())
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<Walk> findWalk(String uuid, LocalDate date) {

        HealthData healthData = findDataByMemberAndDate(uuid, date);

        try {
            return em.createQuery("select w from Walk w where w.healthData.uuid = :uuid", Walk.class)
                    .setParameter("uuid", healthData.getUuid())
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<HealthData> findDataByMember(String uuid) {

        return em.createQuery("select h from HealthData h where h.member.uuid = :uuid", HealthData.class)
                .setParameter("uuid", uuid)
                .getResultList();
    }
}
