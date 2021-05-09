package wallet.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private UUID ID;
    private BigDecimal balanceAmount;
    private BigDecimal minimumBalanceAmount;
    private Currency currency;
    @ManyToOne
    private User user;

    public Wallet(BigDecimal balanceAmount, BigDecimal minimumBalanceAmount, Currency currency, User user) {
        this.balanceAmount = balanceAmount;
        this.minimumBalanceAmount = minimumBalanceAmount;
        this.currency = currency;
        this.user = user;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }
}
