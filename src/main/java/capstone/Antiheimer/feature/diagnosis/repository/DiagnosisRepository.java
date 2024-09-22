package capstone.Antiheimer.feature.diagnosis.repository;

import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

        return em.createQuery("select d from DiagnosisSheet d where d.number = :num ", DiagnosisSheet.class)
                .setParameter("num", num)
                .getSingleResult();
    }

    /**
     * 진단 uuid 생성하기
     * @param uuid
     * @return
     */
    public String generateDiagnosis(String uuid) {

        Diagnosis diagnosis = new Diagnosis();

        String diagnosisUuid = UUID.randomUUID().toString();

        diagnosis.setUuid(diagnosisUuid);
        Member findMember = memberRepository.findOneByUuid(uuid);
        diagnosis.setMember(findMember);
        LocalDate date = LocalDate.now();
        diagnosis.setDiagnosisDate(date);
        diagnosis.setScore(0);

        em.persist(diagnosis);

        return diagnosisUuid;
    }

    public void saveDiagnosis(Diagnosis diagnosis) {

        em.persist(diagnosis);
    }

    public Diagnosis findOneByUuid(String diagnosisUuid) {

        return em.find(Diagnosis.class, diagnosisUuid);
    }
}
