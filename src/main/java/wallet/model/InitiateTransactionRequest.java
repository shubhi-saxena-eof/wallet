package wallet.model;

import wallet.entity.DebitCreditIndicator;

import java.math.BigDecimal;

public class InitiateTransactionRequest {

    private BigDecimal amount;
    private String currency;
    private DebitCreditIndicator debitCreditIndicator;

}
