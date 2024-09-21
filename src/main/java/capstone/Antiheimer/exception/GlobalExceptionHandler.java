package capstone.Antiheimer.exception;

import capstone.Antiheimer.exception.InvalidDataTypeException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.exception.NullIdException;
import capstone.Antiheimer.feature.health_data.dto.RecentDateRes;
import capstone.Antiheimer.util.dto.NormalResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullIdException.class)
    public NormalResDto handleNullIdException(NullIdException e) {
        return new NormalResDto("405", "입력되지 않은 아이디");
    }

    @ExceptionHandler(NullPwException.class)
    public NormalResDto handleNullPwException(NullPwException e) {
        return new NormalResDto("405", "입력되지 않은 비밀번호");
    }

    @ExceptionHandler(InvalidDataTypeException.class)
    public ResponseEntity<NormalResDto> handleInvalidDataType(InvalidDataTypeException e) {
        return new ResponseEntity<>(new NormalResDto("401", "유효하지 않은 데이터 타입"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<RecentDateRes> handleNotExistException(NotExistException e) {
        return new ResponseEntity<>(new RecentDateRes("408", "존재하지 않는 회원", null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<RecentDateRes> handleUnsupportedEncodingException(UnsupportedEncodingException e) {
        return new ResponseEntity<>(new RecentDateRes("410", "디코딩 오류", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 기타 예외 처리
}