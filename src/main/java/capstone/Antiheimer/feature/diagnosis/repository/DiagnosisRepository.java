package capstone.Antiheimer.feature.diagnosis.repository;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiagnosisRepository {

    private final EntityManager em;

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
}
