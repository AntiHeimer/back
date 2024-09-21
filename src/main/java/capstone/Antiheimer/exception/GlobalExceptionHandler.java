package capstone.Antiheimer.exception;

import capstone.Antiheimer.exception.duplicate.*;
import capstone.Antiheimer.exception.incorrect.*;
import capstone.Antiheimer.exception.invalid.*;
import capstone.Antiheimer.exception.notexist.*;
import capstone.Antiheimer.exception.nullE.*;
import capstone.Antiheimer.util.dto.NormalResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 41X: NULL Exception
     */
    @ExceptionHandler(NullUuidException.class)
    public ResponseEntity<NormalResDto> handleNullUuidException(NullUuidException e) {
       NormalResDto resDto = new NormalResDto("410", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullIdException.class)
    public ResponseEntity<NormalResDto> handleNullIdException(NullIdException e) {
        NormalResDto resDto = new NormalResDto("411", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPwException.class)
    public ResponseEntity<NormalResDto> handleNullPwException(NullPwException e) {
        NormalResDto resDto = new NormalResDto("412", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullNameException.class)
    public ResponseEntity<NormalResDto> handleNullNameException(NullNameException e) {
        NormalResDto resDto = new NormalResDto("413", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullGenderException.class)
    public ResponseEntity<NormalResDto> handleNullGenderException(NullGenderException e) {
        NormalResDto resDto = new NormalResDto("414", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullBirthException.class)
    public ResponseEntity<NormalResDto> handleNullBirthException(NullBirthException e) {
        NormalResDto resDto = new NormalResDto("415", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }


    /**
     * 42X: INVALID Exception
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<NormalResDto> handleNullUuidException(InvalidUuidException e) {
        NormalResDto resDto = new NormalResDto("420", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidIdException.class)
    public ResponseEntity<NormalResDto> handleNullIdException(InvalidIdException e) {
        NormalResDto resDto = new NormalResDto("421", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPwException.class)
    public ResponseEntity<NormalResDto> handleNullPwException(InvalidPwException e) {
        NormalResDto resDto = new NormalResDto("422", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<NormalResDto> handleNullNameException(InvalidNameException e) {
        NormalResDto resDto = new NormalResDto("423", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<NormalResDto> handleNullGenderException(InvalidGenderException e) {
        NormalResDto resDto = new NormalResDto("424", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataTypeException.class)
    public ResponseEntity<NormalResDto> handleNullDataTypeException(InvalidDataTypeException e) {
        NormalResDto resDto = new NormalResDto("425", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }


    /**
     * 43X: NOT EXIST Exception
     */
    @ExceptionHandler(NotExistIdException.class)
    public ResponseEntity<NormalResDto> handleNotExistIdException(NotExistIdException e) {
        NormalResDto resDto = new NormalResDto("430", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ResponseEntity<NormalResDto> handleNotExistMemberException(NotExistMemberException e) {
        NormalResDto resDto = new NormalResDto("431", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistLocationException.class)
    public ResponseEntity<NormalResDto> handleNotExistLocationException(NotExistLocationException e) {
        NormalResDto resDto = new NormalResDto("432", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistNotificationException.class)
    public ResponseEntity<NormalResDto> handleNotExistNotificationException(NotExistNotificationException e) {
        NormalResDto resDto = new NormalResDto("433", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }


    /**
     * 44X: INCORRECT Exception
     */
    @ExceptionHandler(IncorrectPwException.class)
    public ResponseEntity<NormalResDto> handleIncorrectPwException(IncorrectPwException e) {
        NormalResDto resDto = new NormalResDto("440", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectNumException.class)
    public ResponseEntity<NormalResDto> handleIncorrectNumException(IncorrectNumException e) {
        NormalResDto resDto = new NormalResDto("441", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }


    /**
     * 45X: DUPLICATE Exception
     */
    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<NormalResDto> handleDuplicateIdException(DuplicateIdException e) {
        NormalResDto resDto = new NormalResDto("450", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateLocationException.class)
    public ResponseEntity<NormalResDto> handleDuplicateLocationException(DuplicateLocationException e) {
        NormalResDto resDto = new NormalResDto("451", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateHealthDataException.class)
    public ResponseEntity<NormalResDto> handleDuplicateHealthDataException(DuplicateHealthDataException e) {
        NormalResDto resDto = new NormalResDto("452", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRelationException.class)
    public ResponseEntity<NormalResDto> handleDuplicateRelationException(DuplicateRelationException e) {
        NormalResDto resDto = new NormalResDto("453", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }


    /**
     * 46X: 기타 Exception
     */
    // 인코딩
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<NormalResDto> handleUnsupportedEncodingException(UnsupportedEncodingException e) {
        NormalResDto resDto = new NormalResDto("460", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }

    // Json
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<NormalResDto> handleJsonProcessingException(JsonProcessingException e) {
        NormalResDto resDto = new NormalResDto("461", e.getMessage());
        return new ResponseEntity<>(resDto, HttpStatus.BAD_REQUEST);
    }
}