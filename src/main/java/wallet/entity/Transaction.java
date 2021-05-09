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
    private Wallet sourceWallet;
    private Wallet targetWallet;
    private TransactionStatus status;

}
