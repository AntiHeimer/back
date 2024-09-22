package capstone.Antiheimer.feature.health_data.repository;

import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.feature.health_data.entity.*;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HealthDataRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    /**
     * 활동 데이터 저장
     * @param reqDto
     */
    public void saveActive(SaveActiveReqDto reqDto) {

        Active active = new Active();

        active.setUuid(UUID.randomUUID().toString());
        active.setDate(reqDto.getDate());
        active.setActiveEnergyBurned(reqDto.getActiveData());

        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());

        // HealthData에서 findMember와 date 값이 있는 인스턴스 찾기
        HealthData findHealthData = findHealthDataByMemberAndDate(findMember.getUuid(), active.getDate());

        if (findHealthData == null) { // findMember와 date가 동시에 존재하는 인스턴스가 없으면

            HealthData newHealthData = new HealthData(); // 새로운 HealthData 인스턴스 생성

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(active.getDate());
            newHealthData.setActive(active);
            active.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else { // findMember와 date가 동시에 존재하는 인스턴스가 있으면

            findHealthData.setActive(active);
            active.setHealthData(findHealthData);

            em.persist(findHealthData);
        }
        em.persist(active);
    }

    /**
     * 움직인 거리 데이터 저장
     * @param reqDto
     */
    public void saveMove(SaveMoveReqDto reqDto) {

        List<MoveVo> moveVoList = reqDto.getMoveData();

        Move move = new Move();

        move.setUuid(UUID.randomUUID().toString());
        move.setDate(reqDto.getDate());

        int sumValue = 0; // 총 움직인 거리

        // 움직인 거리 합산
        for (MoveVo moveVo : moveVoList) {

            sumValue += moveVo.getValue();
        }

        move.setValue(sumValue);

        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());
        HealthData findHealthData = findHealthDataByMemberAndDate(findMember.getUuid(), reqDto.getDate());

        if (findHealthData == null) { // findMember와 date가 동시에 존재하는 인스턴스가 없으면

            HealthData newHealthData = new HealthData(); // 새로운 HealthData 인스턴스 생성

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(reqDto.getDate());
            move.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else { // findMember와 date가 동시에 존재하는 인스턴스가 있으면

            move.setHealthData(findHealthData);

            em.persist(findHealthData);
        }

        em.persist(move);
    }

    /**
     * 걸음수 데이터 저장
     * @param reqDto
     */
    public void saveWalk(SaveWalkReqDto reqDto) {

        List<WalkVo> walkVoList = reqDto.getWalkData();

        Walk walk = new Walk();

        walk.setUuid(UUID.randomUUID().toString());
        walk.setDate(reqDto.getDate());

        int sumValue = 0; // 총 걸음수

        // 걸음수 합산
        for (WalkVo walkVo : walkVoList) {

            sumValue += walkVo.getValue();
        }

        walk.setValue(sumValue);

        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());
        HealthData findHealthData = findHealthDataByMemberAndDate(findMember.getUuid(), reqDto.getDate());

        if (findHealthData == null) { // findMember와 date가 동시에 존재하는 인스턴스가 없으면

            HealthData newHealthData = new HealthData(); // 새로운 HealthData 인스턴스 생성

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(reqDto.getDate());
            walk.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else { // findMember와 date가 동시에 존재하는 인스턴스가 있으면

            walk.setHealthData(findHealthData);

            em.persist(findHealthData);
        }

        em.persist(walk);
    }

    /**
     * 수면 데이터 저장
     * @param reqDto
     */
    public void saveSleep(SaveSleepReqDto reqDto) {

        List<SleepVo> sleepVoList = reqDto.getSleepData();

        Sleep sleep = new Sleep();

        sleep.setUuid(UUID.randomUUID().toString());
        sleep.setDate(reqDto.getDate());

        int sleepTime = 0;
        int rem = 0;
        int core = 0;
        int deep = 0;

        for (SleepVo sleepVo : sleepVoList) {
            switch (sleepVo.getValue()) {
                case "INBED" -> {
                    Duration duration = Duration.between(sleepVo.getStartDateTime(), sleepVo.getEndDateTime());
                    sleepTime += (int) duration.getSeconds();
                }
                case "REM" -> {
                    Duration duration = Duration.between(sleepVo.getStartDateTime(), sleepVo.getEndDateTime());
                    rem += (int) duration.getSeconds();
                }
                case "CORE" -> {
                    Duration duration = Duration.between(sleepVo.getStartDateTime(), sleepVo.getEndDateTime());
                    core += (int) duration.getSeconds();
                }
                case "DEEP" -> {
                    Duration duration = Duration.between(sleepVo.getStartDateTime(), sleepVo.getEndDateTime());
                    deep += (int) duration.getSeconds();
                }
            }
        }
        sleep.setSleepTime(sleepTime);
        sleep.setDeep(deep);
        sleep.setCore(core);
        sleep.setRem(rem);

        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());
        HealthData findHealthData = findHealthDataByMemberAndDate(findMember.getUuid(), reqDto.getDate());

        if (findHealthData == null) {

            HealthData newHealthData = new HealthData();

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(reqDto.getDate());
            sleep.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else {

            sleep.setHealthData(findHealthData);

            em.persist(findHealthData);
        }

        em.persist(sleep);
    }

    /**
     * 몸무게 데이터 저장
     * @param reqDto
     */
    public void saveWeight(SaveWeightReqDto reqDto) {

        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());
        findMember.setWeight(reqDto.getWeight());

        em.persist(findMember);
    }

    /**
     * 건강 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public HealthData findHealthDataByMemberAndDate(String memberUuid, LocalDate date) {

        try {
            return em.createQuery("SELECT h FROM HealthData h WHERE h.date = :date and h.member.uuid = :memberUuid", HealthData.class)
                    .setParameter("memberUuid", memberUuid).setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 최근 저장된 활동 데이터 날짜 조회
     * @return
     */
    public LocalDate findLastSentDateOfActive(String memberUuid) {

        return em.createQuery("SELECT max(a.date) as last_date FROM Active a WHERE a.healthData.member.uuid =:memberUuid", LocalDate.class)
                .setParameter("memberUuid", memberUuid)
                .getSingleResult();
    }

    /**
     * 최근 저장된 움직인 거리 데이터 날짜 조회
     * @param memberUuid
     * @return
     */
    public LocalDate findLastSentDateOfMove(String memberUuid) {

        return em.createQuery("SELECT max(m.date) as last_date FROM Move m WHERE m.healthData.member.uuid = :memberUuid", LocalDate.class)
                .setParameter("memberUuid", memberUuid)
                .getSingleResult();
    }

    /**
     * 최근 저장된 걸음수 데이터 날짜 조회
     * @param memberUuid
     * @return
     */
    public LocalDate findLastSentDateOfWalk(String memberUuid) {

        return em.createQuery("SELECT max(w.date) as last_date FROM Walk w WHERE w.healthData.member.uuid = :memberUuid", LocalDate.class)
                .setParameter("memberUuid", memberUuid)
                .getSingleResult();
    }

    /**
     * 최근 저장된 수면 데이터 날짜 조회
     * @param memberUuid
     * @return
     */
    public LocalDate findLastSentDateOfSleep(String memberUuid) {

        return em.createQuery("SELECT max(s.date) as last_date FROM Sleep s WHERE s.healthData.member.uuid = :memberUuid", LocalDate.class)
                .setParameter("memberUuid", memberUuid)
                .getSingleResult();
    }

    /**
     * 건강 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<HealthData> findHealthDataByMemberUuid(String memberUuid, LocalDate date) {

        LocalDate startDate = date.minusDays(7);

        return em.createQuery("SELECT h FROM HealthData h WHERE h.member.uuid = :memberUuid AND h.date >= :startDate AND h.date <= :date ORDER BY h.date DESC", HealthData.class)
                .setParameter("memberUuid", memberUuid).setParameter("date", date).setParameter("startDate", startDate)
                .getResultList();
    }

    /**
     * 활동 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<Active> findActive(String memberUuid, LocalDate date) {

        LocalDate startDate = date.minusDays(7);

        return em.createQuery("SELECT a FROM Active a WHERE a.healthData.member.uuid = :memberUuid AND a.date >= :startDate AND a.date <= :date ORDER BY a.date DESC", Active.class)
                .setParameter("memberUuid", memberUuid).setParameter("startDate", startDate)
                .getResultList();
    }

    /**
     * 움직인 거리 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<Move> findMove(String memberUuid, LocalDate date) {

        LocalDate startDate = date.minusDays(7);

        return em.createQuery("SELECT m FROM Move m WHERE m.healthData.member.uuid = :memberUuid AND m.date >= :startDate AND m.date <= :date ORDER BY m.date DESC", Move.class)
                .setParameter("memberUuid", memberUuid).setParameter("startDate", startDate)
                .getResultList();
    }

    /**
     * 걸음수 데이터 조회
     * @param memberUuid
     * @param date
     * @return
     */
    public List<Walk> findWalk(String memberUuid, LocalDate date) {

        LocalDate startDate = date.minusDays(7);

        return em.createQuery("SELECT w FROM Walk w WHERE w.healthData.member.uuid = :memberUuid AND w.date >= :startDate AND w.date <= :date ORDER BY w.date DESC", Walk.class)
                .setParameter("memberUuid", memberUuid).setParameter("startDate", startDate)
                .getResultList();
    }

    public List<Sleep> findSleep(String memberUuid, LocalDate date) {

        LocalDate startDate = date.minusDays(7);

        return em.createQuery("SELECT s FROM Sleep s WHERE s.healthData.member.uuid = :memberUuid AND s.date >= :startDate AND s.date <= :date ORDER BY s.date DESC", Sleep.class)
                .setParameter("memberUuid", memberUuid).setParameter("startDate", startDate)
                .getResultList();
    }

    /**
     * 활동 데이터 존재 확인
     * @param reqDto
     * @return
     */
    public boolean isExistActive(SaveActiveReqDto reqDto) {

        List<Active> activeList = em.createQuery("SELECT a FROM Active a WHERE a.healthData.member.uuid = :uuid and a.date = :date", Active.class)
                .setParameter("uuid", reqDto.getMemberUuid()).setParameter("date", reqDto.getDate())
                .getResultList();

        return !activeList.isEmpty();
    }

    /**
     * 움직인 거리 데이터 존재 확인
     * @param reqDto
     * @return
     */
    public boolean isExistMove(SaveMoveReqDto reqDto) {

        List<Move> moveList = em.createQuery("SELECT m FROM Move m WHERE m.healthData.member.uuid = :uuid and m.date = :date", Move.class)
                .setParameter("uuid", reqDto.getMemberUuid()).setParameter("date", reqDto.getDate())
                .getResultList();

        return !moveList.isEmpty();
    }

    /**
     * 걸음수 데이터 존재 확인
     * @param reqDto
     * @return
     */
    public boolean isExistWalk(SaveWalkReqDto reqDto) {

        List<Walk> walkList = em.createQuery("SELECT w FROM Walk w WHERE w.healthData.member.uuid = :uuid and w.date = :date", Walk.class)
                .setParameter("uuid", reqDto.getMemberUuid()).setParameter("date", reqDto.getDate())
                .getResultList();

        return !walkList.isEmpty();
    }

    /**
     * 수면 데이터 존재 확인
     * @param reqDto
     * @return
     */
    public boolean isExistSleep(SaveSleepReqDto reqDto) {

        List<Sleep> sleepList = em.createQuery("SELECT s FROM Sleep s WHERE s.healthData.member.uuid = :uuid and s.date = :date", Sleep.class)
                .setParameter("uuid", reqDto.getMemberUuid()).setParameter("date", reqDto.getDate())
                .getResultList();

        return sleepList.isEmpty();
    }

    /**
     *
     * @param memberUuid
     * @return
     */
    public List<HealthData> findDataByMember(String memberUuid) {

        return em.createQuery("SELECT h FROM HealthData h WHERE h.member.uuid = :memberUuid", HealthData.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }
}
