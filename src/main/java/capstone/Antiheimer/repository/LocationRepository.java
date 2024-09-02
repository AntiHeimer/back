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
        location.setDate(reqDto.getFormattedDate());
        location.setEncryptedLocation(request);

        em.persist(location);
    }

    public boolean existLocation(String request, LocationReqDto reqDto) {

        try {
            em.createQuery("select l from Location l where l.encryptedLocation = : location and l.member.uuid =: uuid", Location.class)
                    .setParameter("location", request).setParameter("uuid", reqDto.getMemberUuid())
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Location findLastLocation(String memberUuid) {

        return em.createQuery("select max(l.date) from Location l where l.member.uuid = : uuid", Location.class)
                .setParameter("uuid", memberUuid)
                .getSingleResult();
    }
}
