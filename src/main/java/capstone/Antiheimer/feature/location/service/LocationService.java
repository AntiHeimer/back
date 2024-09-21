package capstone.Antiheimer.feature.location.service;

import capstone.Antiheimer.exception.notexist.NotExistLocationException;
import capstone.Antiheimer.exception.notexist.NotExistMemberException;
import capstone.Antiheimer.feature.location.entity.Location;
import capstone.Antiheimer.feature.location.repository.LocationRepository;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.exception.duplicate.DuplicateLocationException;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
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

    /**
     * 위치 정보 저장
     * - 회원 존재 확인
     * @param request
     * @param reqDto
     */
    @Transactional
    public void insertLocation(String request, LocationReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid()); // 회원 존재 확인
        locationDuplicateCheck(request, reqDto);
        locationRepository.saveLocation(request, reqDto);
        log.info("위치 정보 저장 성공");
    }

    public Location recentLocation(String memberUuid) {

        memberExistCheck(memberUuid); //회원 존재 확인
        locationExitCheck(memberUuid); //위치 정보 존재 확인
        Location location = locationRepository.findLastLocation(memberUuid);
        log.info("위치 정보 불러오기 성공");
        return location;
    }

    /**
     * 회원 존재 확인
     * @param uuid
     */
    private void memberExistCheck(String uuid) {

        Member findMember = memberRepository.findOneByUuid(uuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistMemberException();
        }
    }

    /**
     * 위치 정보 저장 확인
     * @param request
     * @param reqDto
     */
    private void locationDuplicateCheck(String request, LocationReqDto reqDto) {


        if (locationRepository.existLocation(request, reqDto)) {

            log.warn("이미 존재하는 위치 정보입니다");
            throw new DuplicateLocationException();
        }
    }


    /**
     * 위치 정보 존재 확인
     * @param uuid
     */
    private void locationExitCheck(String uuid) {

        if (!locationRepository.findLocation(uuid)) {

            log.warn("해당 회원의 위치 정보가 존재하지 않습니다");
            throw new NotExistLocationException();
        }
    }
}
