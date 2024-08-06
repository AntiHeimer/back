package capstone.Antiheimer.service;

import capstone.Antiheimer.domain.User;
import capstone.Antiheimer.dto.SignupReqDto;
import capstone.Antiheimer.exception.*;
import capstone.Antiheimer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.springframework.util.StringUtils.containsWhitespace;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SignUpService {

    private final UserRepository userRepository;
    private final AesService aesService;

    @Transactional
    public void signup(SignupReqDto userDto) {

        validateUser(userDto); // 형식 확인
        duplicateUser(userDto); // 중복 확인
        nullUser(userDto); // null 확인
        userRepository.save(userDto);
        log.info("회원가입 성공");
    }

    private void validateUser(SignupReqDto userDto) {

        if (userDto.getId().length() <8 || !userDto.getId().matches("^[a-zA-Z0-9]+$")) {
            log.warn("유효하지 않은 아이디입니다.");
            throw new InvalidIdException();
        }
        if (!userDto.getPw().matches("^[a-zA-Z0-9]+$")) {
            log.warn("유효하지 않은 비밀번호입니다.");
            throw new InvalidPwException();
        }
        if (containsWhitespace(userDto.getName())) {
            log.warn("유효하지 않은 이름입니다.");
            throw new InvalidNameException();
        }
    }

    private void duplicateUser(SignupReqDto userDto) {

        if (!userRepository.findById(userDto.getId()).isEmpty()) {
            log.warn("이미 존재하는 아이디입니다.");
            throw new DuplicateIdException();
        }
        List<User> findUsersId = userRepository.findById(userDto.getId());
        if (!findUsersId.isEmpty()) {
            log.warn("이미 존재하는 회원입니다.");
            throw new DuplicateIdException();
        }
    }

    private void nullUser(SignupReqDto userDto){

        if (userDto.getId().isEmpty()) {
            log.warn("아이디가 비어있습니다.");
            throw new NullIdException();
        }
        if (userDto.getName().isEmpty()) {
            log.warn("이름이 비어있습니다.");
            throw new NullNameException();
        }
        if (userDto.getPw().isEmpty()) {
            log.warn("비밀번호가 비어있습니다.");
            throw new NullPwException();
        }
    }
}
