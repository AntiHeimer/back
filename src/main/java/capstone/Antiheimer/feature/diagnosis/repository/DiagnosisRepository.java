package capstone.Antiheimer.feature.diagnosis.repository;

import capstone.Antiheimer.feature.diagnosis.dto.DementiaResultDto;
import capstone.Antiheimer.feature.diagnosis.entity.Result;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DiagnosisRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    /**
     * 번호로 문제 찾아서 반환
     * @param num
     * @return
     */
    public DiagnosisSheet findQuestion(int num) {

        return em.createQuery("SELECT d FROM DiagnosisSheet d WHERE d.number = :num ", DiagnosisSheet.class)
                .setParameter("num", num)
                .getSingleResult();
    }

    /**
     * 진단 결과 저장하기
     * @param memberUuid
     * @param totalScore
     * @return
     */
    public Diagnosis saveDiagnosis(String memberUuid, int totalScore){

        Diagnosis diagnosis = new Diagnosis();

        String daignosisUuid = UUID.randomUUID().toString();

        diagnosis.setUuid(daignosisUuid);
        Member findMember = memberRepository.findOneByUuid(memberUuid);
        diagnosis.setMember(findMember);
        LocalDate date = LocalDate.now();
        diagnosis.setDiagnosisDate(date);
        diagnosis.setScore(totalScore);

        em.persist(diagnosis);

        return diagnosis;
    }


//
//    /**
//     * 진단 UUID로 진단 찾기
//     * @param diagnosisUuid
//     * @return
//     */
//    public Diagnosis findDiagnosis(String diagnosisUuid) {
//
//        return em.find(Diagnosis.class, diagnosisUuid);
//    }

    /**
     * 멤버 UUID로 진단 찾기
     * @param memberUuid
     * @return
     */
    public List<Diagnosis> findByMemberUuid(String memberUuid) {

        return em.createQuery("SELECT d FROM Diagnosis d WHERE d.member.uuid = :memberUuid ", Diagnosis.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }

    /**
     * 멤버 UUID로 최근 진단 찾기
     * @param memberUuid
     * @return
     */
    public Diagnosis findRecentOneByMemberUuid(String memberUuid) {

        return em.createQuery("SELECT d FROM Diagnosis d WHERE d.member.uuid = :memberUuid ORDER BY d.diagnosisDate DESC", Diagnosis.class)
                .setParameter("memberUuid", memberUuid)
                .setMaxResults(1)
                .getSingleResult();
    }

    /**
     * 결과 저장
     * @param resultDto
     * @return
     */
    public Result saveResult(DementiaResultDto resultDto) {

        Result result = new Result();

        String resultUuid = UUID.randomUUID().toString();

        result.setUuid(resultUuid);
        Member findMember = memberRepository.findOneByUuid(resultDto.getMemberUuid());
        result.setMember(findMember);
        result.setDate(resultDto.getDate());
        result.setStage(resultDto.getStage());
        result.setExplanation(resultDto.getExplanation());

        em.persist(result);

        return result;
    }

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Result> findResultList(String memberUuid) {

        return em.createQuery("SELECT r FROM Result r WHERE r.member.uuid = :memberUuid", Result.class)
                .setParameter("memberUuid", memberUuid)
                .getResultList();
    }
}
