package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.User;
import capstone.Antiheimer.dto.SignupUserReqDto;
import capstone.Antiheimer.service.BcryptService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(SignupUserReqDto userDto) {

        User user = new User();
        BcryptService bcryptService = new BcryptService();

        user.setUuid(UUID.randomUUID().toString());
        user.setId(userDto.getId());
        user.setName(userDto.getName());

        //parent.setPw(parentDTO.getPw());
        String pw = userDto.getPw();
        user.setPw(bcryptService.encode(pw));

        em.persist(user);
    }

    public User findOneById(String id) {

        return em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public User findOneByUuid(String uuid) {

        return em.find(User.class, uuid);
    }



}
