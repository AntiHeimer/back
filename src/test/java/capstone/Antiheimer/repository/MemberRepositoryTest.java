package capstone.Antiheimer.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

//    @Test
//    void findByToken() throws Exception{
//
//        // given
//        Member member = new Member();
//        member.setId("asdfg");
//        member.setPw("kkkkk");
//        member.setGender("female");
//        member.setUuid("bnmbnm-mm");
//        member.setName("강와와");
//        member.setDeviceToken("kfjalkej");
//
//        System.out.println("member = " + member.getDeviceToken());
//
//        // when
//        String token = memberRepository.findTokenById(member.getId());
//
//        // then
//        assertEquals(member.getDeviceToken(), token);
//    }
}