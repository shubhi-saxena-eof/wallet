package wallet.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private BigDecimal amount;
    private Currency currency;
    private ZonedDateTime initiationDateTime;
    private ZonedDateTime settlementDateTime;
    private Wallet wallet;
    private DebitCreditIndicator debitCreditIndicator;
    private TransactionStatus status;

}
