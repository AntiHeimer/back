package capstone.Antiheimer.exception.duplicate;

public class DuplicateIdException extends RuntimeException{

    public DuplicateIdException() { super("이미 존재하는 아이디"); }
}
