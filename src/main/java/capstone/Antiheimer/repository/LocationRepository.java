package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.Location;
import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.LocationReqDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    /**
     * 위치 저장
     * @param request
     */
    public void saveLocation(String request, LocationReqDto reqDto) {

        Location location = new Location();

        location.setUuid(UUID.randomUUID().toString());
        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());
        location.setMember(findMember);
        location.setEncryptedLocation(request);

        em.persist(location);
    }

    public boolean existLocation(String request) {

        try {
            em.createQuery("select l from Location l where l.encryptedLocation = :location ", Location.class)
                    .setParameter("location", request)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
