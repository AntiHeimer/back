package capstone.Antiheimer.exception.duplicate;

public class DuplicateHealthDataException extends RuntimeException {

    public DuplicateHealthDataException() { super("이미 존재하는 건강 데이터"); }
}
