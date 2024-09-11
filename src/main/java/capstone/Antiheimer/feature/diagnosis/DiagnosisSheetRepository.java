package capstone.Antiheimer.feature.diagnosis;

import capstone.Antiheimer.feature.diagnosis.entity.DiagnosisSheet;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiagnosisSheetRepository {

    private final EntityManager em;

    public DiagnosisSheet findQuestion(int num) {

        return em.createQuery("select d from DiagnosisSheet d where d.number = :num ", DiagnosisSheet.class)
                .setParameter("num", num)
                .getSingleResult();
    }
}
