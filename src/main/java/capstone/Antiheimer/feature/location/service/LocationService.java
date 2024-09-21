package capstone.Antiheimer.feature.location.service;

import capstone.Antiheimer.exception.notexist.NotExistLocationException;
import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.feature.location.repository.LocationRepository;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.exception.duplicate.DuplicateLocationException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.util.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final CheckService checkService;

    /**
     * 위치 정보 저장
     * - 회원 존재 확인
     * @param reqDto
     * @param reqDto
     */
    @Transactional
    public void insertLocation(String request, LocationReqDto reqDto) {

        checkService.checkMemberExists(reqDto.getMemberUuid()); // 회원 존재 확인
        checkService.checkLocationDuplicate(request, reqDto);
        locationRepository.saveLocation(request, reqDto);
        log.info("[Service] 위치 정보 저장 성공");
    }

    public Location recentLocation(String memberUuid) {

        checkService.checkMemberExists(memberUuid); // 회원 존재 확인
        checkService.checkLocationExits(memberUuid); // 위치 정보 존재 확인

        Location location = locationRepository.findLastLocation(memberUuid);
        log.info("[Service] 위치 정보 불러오기 성공");
        return location;
    }
}
