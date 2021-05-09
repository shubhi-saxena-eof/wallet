package wallet.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateWalletRequest {

    @NotNull
    private BigDecimal minimumBalanceAmount;
    @NotBlank
    private String currency;

    public BigDecimal getMinimumBalanceAmount() {
        return minimumBalanceAmount;
    }

    public String getCurrency() {
        return currency;
    }
}
