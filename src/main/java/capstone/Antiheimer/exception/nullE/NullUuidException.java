package capstone.Antiheimer.exception.nullE;

public class NullUuidException extends RuntimeException {
    public NullUuidException() {
        super("입력되지 않은 uuid");
    }
}
