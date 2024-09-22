package capstone.Antiheimer.feature.location.service;

import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.feature.location.repository.LocationRepository;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.util.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final MemberRepository memberRepository;
    private final CheckService checkService;

    /**
     * 위치 정보 저장
     * - 회원 존재 확인
     * @param request
     * @param reqDto
     */
    @Transactional
    public void saveLocation(String request, LocationReqDto reqDto) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(reqDto.getMemberUuid());

        log.info("[Service] 위치 중복 확인");
        checkService.checkLocationDuplicate(request, reqDto);

        Location location = convertToEntity(request, reqDto);

        log.info("[Service] 위치 정보 저장");
        locationRepository.saveLocation(location);
    }

    /**
     * 최근 위치 정보 조회
     * @param memberUuid
     * @return
     */
    public Location recentLocation(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 위치 존재 확인");
        checkService.checkLocationExits(memberUuid);

        log.info("[Service] 최근 위치 정보 조회");
        return locationRepository.findRecentLocation(memberUuid);
    }

    public Location convertToEntity(String request, LocationReqDto reqDto) {

        Location location = new Location();

        location.setUuid(UUID.randomUUID().toString());
        Member findMember = memberRepository.findOneByUuid(reqDto.getMemberUuid());
        location.setMember(findMember);
        location.setDate(reqDto.getFormattedDate());
        location.setEncryptedLocation(request);

        return location;
    }
}
