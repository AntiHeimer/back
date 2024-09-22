package capstone.Antiheimer.feature.member.repository;

import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.util.encrypt.BcryptService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /**
     * 회원 저장
     * @param member
     */
    public void save(Member member) {

        em.persist(member);
    }

    /**
     * uuid로 회원 찾기
     * @param memberUuid
     * @return
     */
    public Member findOneByUuid(String memberUuid) {

        return em.find(Member.class, memberUuid);
    }

    /**
     * id로 회원 한 명 찾기(id가 확실히 존재할 때)
     * @param id
     * @return
     */
    public Member findOneById(String id) {

        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    /**
     * id로 회원 여러 명 찾기(회원이 존재하는지 확인할 때)
     * @param id
     * @return
     */
    public List<Member> findById(String id) {

        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }
}
