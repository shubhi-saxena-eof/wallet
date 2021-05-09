package wallet.exception;

public class TransactAPIException extends Exception {

    public TransactAPIException(String messsage, Throwable cause) {
        super(messsage, cause);
    }

    public TransactAPIException(String messsage) {
        super(messsage);
    }

}
