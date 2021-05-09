package wallet.exception;

public class UserAPIException extends Exception {

    public UserAPIException(String messsage, Throwable cause) {
        super(messsage, cause);
    }

    public UserAPIException(String messsage) {
        super(messsage);
    }

}
