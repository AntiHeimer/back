package capstone.Antiheimer.feature.dementia_result.repository;

import capstone.Antiheimer.feature.dementia_result.entity.Result;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DementiaResultRepository {

    private final EntityManager em;

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
