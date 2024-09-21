package capstone.Antiheimer.feature.location.repository;

import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
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

    /**
     * 위치 저장
     * @param location
     */
    public void saveLocation(Location location) {

        em.persist(location);
    }

    /**
     * 위치 존재 확인
     * @param request
     * @param reqDto
     * @return
     */
    public boolean isExistLocation(String request, LocationReqDto reqDto) {

        List<Location> findLocation = em.createQuery("select l from Location l where l.encryptedLocation = :location and l.member.uuid = :memberUuid", Location.class)
                .setParameter("location", request).setParameter("memberUuid", reqDto.getMemberUuid())
                .getResultList();

        return !findLocation.isEmpty();
    }

    /**
     * 최근 위치 정보 조회 조회
     * @param memberUuid
     * @return
     */
    public Location findRecentLocation(String memberUuid) {

        return em.createQuery("select l from Location l where l.member.uuid = :uuid and l.date = (SELECT MAX(l.date) FROM Location l WHERE l.member.uuid = :memberUuid)", Location.class)
                .setParameter("memberUuid", memberUuid)
                .getSingleResult();
    }

    /**
     * 위치 정보 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Location> findLocation(String memberUuid) {

        return em.createQuery("select l from Location l where l.member.uuid = :uuid", Location.class)
                .setParameter("uuid", memberUuid)
                .getResultList();
    }
}
