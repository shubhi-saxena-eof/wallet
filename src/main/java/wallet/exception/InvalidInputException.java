package wallet.exception;

public class InvalidInputException extends Exception{

    public InvalidInputException(String messsage, Throwable cause) {
        super(messsage, cause);
    }

    public InvalidInputException(String messsage) {
        super(messsage);
    }

}
