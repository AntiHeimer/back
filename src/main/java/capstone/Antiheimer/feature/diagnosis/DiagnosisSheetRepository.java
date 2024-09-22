package capstone.Antiheimer.feature.diagnosis;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiagnosisSheetRepository {

    private final EntityManager em;

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
}
