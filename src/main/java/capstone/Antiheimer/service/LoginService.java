package capstone.Antiheimer.service;

import capstone.Antiheimer.Jwt.JwtTokenUtil;
import capstone.Antiheimer.domain.User;
import capstone.Antiheimer.dto.LoginReqDto;
import capstone.Antiheimer.exception.IncorrectPwException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final BcryptService bcryptService;
    private final JwtTokenUtil jwtTokenUtil;


    public String login(LoginReqDto request) {

        userExistCheck(request.getId());
        userCorrectPw(request);

        String token = jwtTokenUtil.generateToken(request).getAccessToken();

        return null;
    }

    private void userExistCheck(String id) {

        User findUser = userRepository.findOneById(id);

        if (findUser == null) {

            log.warn("존재하지 않는 회원입니다");
            throw new NotExistException();
        }
    }

    private void userCorrectPw(LoginReqDto request) {

        User findUser = userRepository.findOneById(request.getId());

        if (!bcryptService.isPwMatch(request.getPw(), findUser.getPw())) {

            log.warn("비밀번호가 일치하지 않습니다");
            throw new IncorrectPwException();
        }
    }
}
