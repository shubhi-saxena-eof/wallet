package wallet.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public class InitiateTransactionRequest {

    @NotNull
    @Positive
    private BigDecimal amount;
    @NotBlank
    private String currency;
    @NotNull
    private UUID sourceWalletID;
    @NotNull
    private UUID targetWalletID;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public UUID getSourceWalletID() {
        return sourceWalletID;
    }

    public UUID getTargetWalletID() {
        return targetWalletID;
    }
}
