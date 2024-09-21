package capstone.Antiheimer.exception.invalid;

public class InvalidUuidException extends RuntimeException {

    public InvalidUuidException() {
        super("유효하지 않은 UUID");
    }
}
