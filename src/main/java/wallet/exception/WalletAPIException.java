package wallet.exception;

public class WalletAPIException extends Exception {

    public WalletAPIException(String messsage, Throwable cause) {
        super(messsage, cause);
    }

    public WalletAPIException(String messsage) {
        super(messsage);
    }

}
