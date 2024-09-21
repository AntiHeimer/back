package capstone.Antiheimer.feature.location.repository;

import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    /**
     * 위치 저장
     * @param reqDto
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
            em.createQuery("select l from Location l where l.encryptedLocation = :location and l.member.uuid = :uuid", Location.class)
                    .setParameter("location", reqDto).setParameter("uuid", reqDto.getMemberUuid())
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Location findLastLocation(String memberUuid) {

        return em.createQuery("select l from Location l where l.member.uuid = :uuid and l.date = (SELECT MAX(l.date) FROM Location l WHERE l.member.uuid = :memberUuid)", Location.class)
                .setParameter("memberUuid", memberUuid)
                .getSingleResult();
    }

    public boolean findLocation(String memberUuid) {

        List<Location> locationList = em.createQuery("select l from Location l where l.member.uuid = :uuid", Location.class)
                .setParameter("uuid", memberUuid)
                .getResultList();
        return !locationList.isEmpty();
    }
}
