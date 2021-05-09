package wallet.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
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

}
