package wallet.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {

    private UUID ID;
    private BigDecimal balanceAmount;
    private BigDecimal minimumBalanceAmount;
    private Currency currency;
    private User user;

}
