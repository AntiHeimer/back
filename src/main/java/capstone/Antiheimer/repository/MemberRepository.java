package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.service.BcryptService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(SignupReqDto memberDto) {

        Member member = new Member();
        BcryptService bcryptService = new BcryptService();

        member.setUuid(UUID.randomUUID().toString());
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());

        //parent.setPw(parentDTO.getPw());
        String pw = memberDto.getPw();
        member.setPw(bcryptService.encode(pw));

        em.persist(member);
    }

    public Member findOneById(String id) {

        return em.createQuery("select u from Member u where u.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Member findOneByUuid(String uuid) {

        return em.find(Member.class, uuid);
    }

    public List<Member> findById(String id) {

        return em.createQuery("select u from Member u where u.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }
}
