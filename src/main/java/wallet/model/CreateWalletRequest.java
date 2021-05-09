package wallet.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class CreateWalletRequest {

    @NotNull
    @PositiveOrZero
    private BigDecimal minimumBalanceAmount;
    @NotBlank
    private String currency;
    @NotNull
    @PositiveOrZero
    private BigDecimal initialBalanceAmount;

}
