package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.dto.LocationReqDto;
import capstone.Antiheimer.exception.DuplicateLocationException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.repository.LocationRepository;
import capstone.Antiheimer.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertLocation(String request, LocationReqDto reqDto) {

        memberExistCheck(reqDto.getMemberUuid()); // 회원 존재 확인
        locationDuplicateCheck(request);
        locationRepository.saveLocation(request);
        log.info("위치 정보 저장 성공");
    }

    /**
     * 회원 존재 확인
     * @param uuid
     */
    private void memberExistCheck(String uuid) {

        Member findMember = memberRepository.findOneByUuid(uuid);

        if (findMember == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistException();
        }
    }

    /**
     * 위치 정보 저장 확인
     * @param request
     */
    private void locationDuplicateCheck(String request) {


        if (locationRepository.existLocation(request)) {

            log.warn("이미 존재하는 위치 정보입니다");
            throw new DuplicateLocationException();
        }
    }
}
