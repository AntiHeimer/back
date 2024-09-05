package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.*;
import capstone.Antiheimer.dto.*;
import capstone.Antiheimer.member.Member;
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
     * @param request
     */
    public void saveActive(SaveActiveReqDto request) {

        Active active = new Active();


        active.setUuid(UUID.randomUUID().toString());
        active.setDate(request.getDate());
        active.setActiveEnergyBurned(request.getActiveData());

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());

        // HealthData에서 findMember와 date 값이 있는 인스턴스 찾기
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), active.getDate());

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

//    public void saveTest(Map<LocalDate, List<SaveActiveReqDto>> request) {
//
//        Active active = new Active();
//
//
//        request.forEach((date, saveActiveReqDtoList) -> {
//            // 각 날짜에 해당하는 데이터 리스트를 처리하는 로직
//            System.out.println("Date: " + date);
//            for (SaveActiveReqDto data : saveActiveReqDtoList) {
//                System.out.println(data);
//                // 여기서 데이터베이스에 저장하는 로직을 추가할 수 있습니다.
//            }
//        });
//    }

    /**
     * 움직인 거리 데이터 저장
     * @param request
     */
    public void saveMove(SaveMoveReqDto request) {

        List<MoveVo> moveVoList = request.getMoveData();

        Move move = new Move();

        move.setUuid(UUID.randomUUID().toString());
        move.setDate(request.getDate());

        int sumValue = 0; // 총 움직인 거리

        // 움직인 거리 합산
        for (MoveVo moveVo : moveVoList) {

            sumValue += moveVo.getValue();
        }

        move.setValue(sumValue);

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

        if (findHealthData == null) { // findMember와 date가 동시에 존재하는 인스턴스가 없으면

            HealthData newHealthData = new HealthData(); // 새로운 HealthData 인스턴스 생성

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(request.getDate());
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
     * @param request
     */
    public void saveWalk(SaveWalkReqDto request) {

        List<WalkVo> walkVoList = request.getWalkData();

        Walk walk = new Walk();

        walk.setUuid(UUID.randomUUID().toString());
        walk.setDate(request.getDate());

        int sumValue = 0; // 총 걸음수

        // 걸음수 합산
        for (WalkVo walkVo : walkVoList) {

            sumValue += walkVo.getValue();
        }

        walk.setValue(sumValue);

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

        if (findHealthData == null) { // findMember와 date가 동시에 존재하는 인스턴스가 없으면

            HealthData newHealthData = new HealthData(); // 새로운 HealthData 인스턴스 생성

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(request.getDate());
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
     * @param request
     */
    public void saveSleep(SaveSleepReqDto request) {

        List<SleepVo> sleepVoList = request.getSleepData();

        Sleep sleep = new Sleep();

        sleep.setUuid(UUID.randomUUID().toString());
        sleep.setDate(request.getDate());

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

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        HealthData findHealthData = findDataByMemberAndDate(findMember.getUuid(), request.getDate());

        if (findHealthData == null) {

            HealthData newHealthData = new HealthData();

            newHealthData.setUuid(UUID.randomUUID().toString());
            newHealthData.setMember(findMember);
            newHealthData.setDate(request.getDate());
            sleep.setHealthData(newHealthData);

            em.persist(newHealthData);
        } else {

            sleep.setHealthData(findHealthData);

            em.persist(findHealthData);
        }

        em.persist(sleep);
    }

    /**
     * 몸무게 저장
     * @param request
     */
    public void saveWeight(SaveWeightReqDto request) {

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());
        findMember.setWeight(request.getWeight());

        em.persist(findMember);
    }

    /**
     * 회원과 날짜로 건강 데이터 찾기
     * @param uuid
     * @param date
     * @return
     */
    public HealthData findDataByMemberAndDate(String uuid, LocalDate date) {

        try {
            return em.createQuery("select h from HealthData h where h.date = :date and h.member.uuid = :uuid", HealthData.class)
                    .setParameter("uuid", uuid).setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 활동 데이터가 며칠까지 전송됐는지 찾기
     * @return
     */
    public LocalDate findLastSentDateOfActive(String uuid) {

        return em.createQuery("select max(a.date) as last_date from Active a where a.healthData.member.uuid =:uuid", LocalDate.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    /**
     * 움직인 거리 데이터가 며칠까지 전송됐는지 찾기
     * @return
     */
    public LocalDate findLastSentDateOfMove(String uuid) {

        return em.createQuery("select max(m.date) as last_date from Move m where m.healthData.member.uuid = :uuid", LocalDate.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    /**
     * 걸음수 데이터가 며칠까지 전송됐는지 찾기
     * @return
     */
    public LocalDate findLastSentDateOfWalk(String uuid) {

        return em.createQuery("select max(w.date) as last_date from Walk w where w.healthData.member.uuid = :uuid", LocalDate.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    /**
     * 수면 데이터가 며칠까지 전송됐는지 찾기
     * @param uuid
     * @return
     */
    public LocalDate findLastSentDateOfSleep(String uuid) {

        return em.createQuery("select max(s.date) as last_date from Sleep s where s.healthData.member.uuid = :uuid", LocalDate.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
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

    /**
     * 활동 데이터 존재 확인
     * @param request
     * @return
     */
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

    /**
     * 움직인 거리 데이터 존재 확인
     * @param request
     * @return
     */
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

    /**
     * 걸음수 데이터 존재 확인
     * @param request
     * @return
     */
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

    /**
     * 수면 데이터 존재 확인
     * @param request
     * @return
     */
    public boolean existSleep(SaveSleepReqDto request) {

        try {
            em.createQuery("select s from Sleep s where s.healthData.member.uuid = :uuid and s.date = :date", Sleep.class)
                    .setParameter("uuid", request.getMemberUuid()).setParameter("date", request.getDate())
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     *
     * @param uuid
     * @return
     */
    public List<HealthData> findDataByMember(String uuid) {

        return em.createQuery("select h from HealthData h where h.member.uuid = :uuid", HealthData.class)
                .setParameter("uuid", uuid)
                .getResultList();
    }
}
