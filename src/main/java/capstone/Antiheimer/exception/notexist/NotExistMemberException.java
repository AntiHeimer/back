package capstone.Antiheimer.exception.notexist;

public class NotExistMemberException extends RuntimeException {

    public NotExistMemberException() { super("존재하지 않는 회원"); }
}
