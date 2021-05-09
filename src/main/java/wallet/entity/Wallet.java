package wallet.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import wallet.model.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Wallet {

    @Id
    @GeneratedValue
    private UUID ID;
    @NotNull
    private BigDecimal balanceAmount;
    @NotNull
    private BigDecimal minimumBalanceAmount;
    @NotNull
    private Currency currency;
    @OneToOne
    @JoinColumn(unique = true, name = "user_id")
    @NotNull
    private User user;

    public Wallet(BigDecimal balanceAmount, BigDecimal minimumBalanceAmount, Currency currency, User user) {
        this.balanceAmount = balanceAmount;
        this.minimumBalanceAmount = minimumBalanceAmount;
        this.currency = currency;
        this.user = user;
    }

}
