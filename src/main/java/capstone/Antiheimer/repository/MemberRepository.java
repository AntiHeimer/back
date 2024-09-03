package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.dto.TokenReqDto;
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

    /**
     * 회원 저장
     * @param memberDto
     */
    public void save(SignupReqDto memberDto) {

        Member member = new Member();
        BcryptService bcryptService = new BcryptService();

        member.setUuid(UUID.randomUUID().toString());
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());
        member.setBirth(memberDto.getBirth());
        member.setGender(memberDto.getGender());

        String pw = memberDto.getPw();
        member.setPw(bcryptService.encode(pw)); // bcrypt 암호화

        em.persist(member);
    }

    /**
     * 디바이스 토큰 저장
     * @param tokenReqDto
     */
    public void updateDeviceToken(TokenReqDto tokenReqDto) {

        Member member = findOneByUuid(tokenReqDto.getMemberUuid());
        member.setDeviceToken(tokenReqDto.getDeviceToken());

        em.persist(member);
    }

    /**
     * uuid로 회원 찾기
     * @param uuid
     * @return
     */
    public Member findOneByUuid(String uuid) {

        return em.find(Member.class, uuid);
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

    public String findTokenById(String id) {

        return em.createQuery("SELECT m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .map(Member::getDeviceToken)
                .orElse(null);
    }
}
