package capstone.Antiheimer.feature.dementia_result.repository;

import capstone.Antiheimer.feature.dementia_result.dto.ResultDto;
import capstone.Antiheimer.feature.dementia_result.entity.Result;
import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
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
public class DementiaResultRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    /**
     * 결과 저장
     * @param resultDto
     * @return
     */
    public Result saveResult(ResultDto resultDto) {

        Result result = new Result();

        String resultUuid = UUID.randomUUID().toString();

        result.setUuid(resultUuid);
        Member findMember = memberRepository.findOneByUuid(resultDto.getMemberUuid());
        result.setMember(findMember);
        result.setDate(resultDto.getDate());
        result.setStage(result.getStage());
        result.setExplanation(result.getExplanation());


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
