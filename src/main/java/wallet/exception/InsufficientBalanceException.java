package wallet.exception;

public class InsufficientBalanceException extends Exception{

    public InsufficientBalanceException(String messsage, Throwable cause) {
        super(messsage, cause);
    }

    public InsufficientBalanceException(String messsage) {
        super(messsage);
    }

}
