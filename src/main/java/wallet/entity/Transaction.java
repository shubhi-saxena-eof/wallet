package wallet.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import wallet.model.Currency;
import wallet.model.TransactionStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    private ZonedDateTime initiationDateTime;
    private ZonedDateTime settlementDateTime;
    @NotNull
    private UUID sourceWalletID;
    @NotNull
    private UUID targetWalletID;
    @NotNull
    private TransactionStatus status;

    public Transaction(BigDecimal amount, Currency currency, ZonedDateTime initiationDateTime, UUID sourceWalletID, UUID targetWalletID, TransactionStatus status) {
        this.amount = amount;
        this.currency = currency;
        this.initiationDateTime = initiationDateTime;
        this.sourceWalletID = sourceWalletID;
        this.targetWalletID = targetWalletID;
        this.status = status;
    }

}
