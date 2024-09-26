package capstone.Antiheimer.util;

import capstone.Antiheimer.exception.duplicate.DuplicateHealthDataException;
import capstone.Antiheimer.exception.duplicate.DuplicateIdException;
import capstone.Antiheimer.exception.duplicate.DuplicateLocationException;
import capstone.Antiheimer.exception.duplicate.DuplicateRelationException;
import capstone.Antiheimer.exception.incorrect.IncorrectPwException;
import capstone.Antiheimer.exception.invalid.*;
import capstone.Antiheimer.exception.notexist.*;
import capstone.Antiheimer.exception.nullE.*;
import capstone.Antiheimer.feature.health_data.dto.*;
import capstone.Antiheimer.feature.health_data.entity.HealthData;
import capstone.Antiheimer.feature.health_data.repository.HealthDataRepository;
import capstone.Antiheimer.feature.location.dto.LocationReqDto;
import capstone.Antiheimer.feature.location.repository.LocationRepository;
import capstone.Antiheimer.feature.member.dto.LoginReqDto;
import capstone.Antiheimer.feature.member.dto.SignupReqDto;
import capstone.Antiheimer.feature.member.entity.Member;
import capstone.Antiheimer.feature.member.repository.MemberRepository;
import capstone.Antiheimer.feature.notification.entity.Notification;
import capstone.Antiheimer.feature.relation.entity.Relation;
import capstone.Antiheimer.feature.relation.repository.RelationRepository;
import capstone.Antiheimer.util.encrypt.BcryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.StringUtils.containsWhitespace;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckService {

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final HealthDataRepository healthDataRepository;
    private final RelationRepository relationRepository;
    private final BcryptService bcryptService;

    /**
     * uuid 공백 확인
     * @param memberUuid
     */
    public void checkUuidNotNull(String memberUuid) {

        if (memberUuid.isEmpty()) {

            log.warn("uuid가 비어있습니다");
            throw new NullUuidException();
        }
    }

    /**
     * 회원 정보 공백 확인
     * @param memberDto
     */
    public void checkMemberNotNull(SignupReqDto memberDto){

        if (memberDto.getId().isEmpty()) {

            log.warn("아이디가 비어있습니다");
            throw new NullIdException();
        }
        if (memberDto.getName().isEmpty()) {

            log.warn("이름이 비어있습니다");
            throw new NullNameException();
        }
        if (memberDto.getPw().isEmpty()) {

            log.warn("비밀번호가 비어있습니다");
            throw new NullPwException();
        }
        if (memberDto.getGender().isEmpty()) {

            log.warn("성별이 비어있습니다");
            throw new NullGenderException();
        }
        if (memberDto.getBirth() == null) {

            log.warn("생일이 비어있습니다");
            throw new NullBirthException();
        }
    }

    /**
     * uuid 유효성 확인
     * @param memberUuid
     */
    public void checkUuidValid(String memberUuid) {

        if (containsWhitespace(memberUuid) || memberUuid.length() != 36) {

            log.warn("유효하지 않은 uuid입니다");
            throw new InvalidUuidException();
        }
    }

    /**
     * 회원 정보 유효성 확인
     * @param memberDto
     */
    public void checkMemberValid(SignupReqDto memberDto) {

        if (memberDto.getId().length() < 8 || !memberDto.getId().matches("^[a-zA-Z0-9]+$")) {

            log.warn("유효하지 않은 아이디입니다");
            throw new InvalidIdException();
        }
        if (!memberDto.getPw().matches("^[a-zA-Z0-9]+$")) {

            log.warn("유효하지 않은 비밀번호입니다");
            throw new InvalidPwException();
        }
        if (containsWhitespace(memberDto.getName())) {

            log.warn("유효하지 않은 이름입니다");
            throw new InvalidNameException();
        }
        if (!memberDto.getGender().equals("female") && !memberDto.getGender().equals("male")) {

            log.warn("유효하지 않은 성별입니다");
            throw new InvalidGenderException();
        }
    }

    /**
     * 데이터 타입 중복 유효성 확인
     * @param data
     */
    public void checkDataTypeValid(String data) {

        if (!data.equals("active") &&  !data.equals("move") && !data.equals("walk") && !data.equals("sleep")) {

            log.warn("유효하지 않은 데이터 타입입니다");
            throw new InvalidDataTypeException();
        }
    }

    /**
     * 회원 존재 확인
     * @param memberUuid
     */
    public void checkMemberExists(String memberUuid) {

        if (memberRepository.findOneByUuid(memberUuid) == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistMemberException();
        }
    }

    /**
     * 아이디 존재 확인
     * @param id
     */
    public void checkIdExists(String id) {

        if (memberRepository.findById(id).isEmpty()) {

            log.warn("존재하지 않는 아이디입니다");
            throw new NotExistIdException();
        }
    }

    /**
     * 위치 정보 존재 확인
     * @param memberUuid
     */
    public void checkLocationExits(String memberUuid) {

        if (locationRepository.findLocationList(memberUuid).isEmpty()) {

            log.warn("존재하지 않는 위치 정보입니다");
            throw new NotExistLocationException();
        }
    }

    /**
     * 건강 데이터 존재 확인
     * @param reqDto
     */
    public void checkHealthDataExists(FindHealthDataReqDto reqDto) {

        List<HealthData> healthDataList = healthDataRepository.findHealthDataList(reqDto.getMemberUuid(), reqDto.getDate());

        if (healthDataList.isEmpty()) {

            log.warn("존재하지 않는 건강 데이터입니다");
            throw new NotExistHealthDataException();
        }
    }

    /**
     * 알림 존재 확인
     * @param notification
     */
    public void checkNotificationExists(Notification notification) {

        if (notification == null) {

            log.warn("존재하지 않는 알림입니다");
            throw new NotExistNotificationException();
        }
    }

    /**
     * 비밀번호 일치 확인
     * @param reqDto
     */
    public void checkPwMatches(LoginReqDto reqDto) {

        Member findMember = memberRepository.findOneById(reqDto.getId());

        if (!bcryptService.isPwMatch(reqDto.getPw(), findMember.getPw())) {

            log.warn("비밀번호가 일치하지 않습니다");
            throw new IncorrectPwException();
        }
    }

    /**
     * 아이디 중복 확인
     * @param memberDto
     */
    public void checkDuplicateId(SignupReqDto memberDto) {

        if (!memberRepository.findById(memberDto.getId()).isEmpty()) {

            log.warn("이미 존재하는 아이디입니다");
            throw new DuplicateIdException();
        }
    }

    /**
     * 위치 정보 중복 확인
     * @param request
     * @param reqDto
     */
    public void checkLocationDuplicate(String request, LocationReqDto reqDto) {

        if (locationRepository.isExistLocation(request, reqDto)) {

            log.warn("이미 존재하는 위치 정보입니다");
            throw new DuplicateLocationException();
        }
    }

    /**
     * 걸음수 데이터 존재 확인
     * @param reqDto
     */
    public void checkWalkDataDuplicate(SaveWalkReqDto reqDto) {

        if (healthDataRepository.isExistWalk(reqDto)) {

            log.warn("이미 존재하는 걸음수 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 활동 데이터 증복 확인
     * @param reqDto
     */
    public void checkActiveDataDuplicate(SaveActiveReqDto reqDto) {

        if (healthDataRepository.isExistActive(reqDto)) {

            log.warn("이미 존재하는 활동 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 움직인 거리 데이터 중복 확인
     * @param reqDto
     */
    public void checkMoveDataDuplicate(SaveMoveReqDto reqDto) {

        if (healthDataRepository.isExistMove(reqDto)) {

            log.warn("이미 존재하는 움직인 거리 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 수면 데이터 중복 확인
     * @param reqDto
     */
    public void checkSleepDataDuplicate(SaveSleepReqDto reqDto) {

        if (healthDataRepository.isExistSleep(reqDto)) {

            log.warn("이미 존재하는 수면 데이터입니다");
            throw new DuplicateHealthDataException();
        }
    }

    /**
     * 관계 중복 확인
     * @param relation
     */
    public void checkDuplicateRelation(Relation relation) {

        if (!relationRepository.isRelationExist(relation)) {

            log.warn("이미 존재하는 관계입니다");
            throw new DuplicateRelationException();
        }
    }
}