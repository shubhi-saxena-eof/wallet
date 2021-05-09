package wallet.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    private ZonedDateTime initiationDateTime;
    private ZonedDateTime settlementDateTime;
    @NotNull
    @OneToOne
    private Wallet sourceWallet;
    @NotNull
    @OneToOne
    private Wallet targetWallet;
    @NotNull
    private TransactionStatus status;

    public Transaction(BigDecimal amount, Currency currency, ZonedDateTime initiationDateTime, Wallet sourceWallet, Wallet targetWallet, TransactionStatus status) {
        this.amount = amount;
        this.currency = currency;
        this.initiationDateTime = initiationDateTime;
        this.sourceWallet = sourceWallet;
        this.targetWallet = targetWallet;
        this.status = status;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setSettlementDateTime(ZonedDateTime settlementDateTime) {
        this.settlementDateTime = settlementDateTime;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
