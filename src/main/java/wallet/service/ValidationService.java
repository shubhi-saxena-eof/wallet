package wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.entity.Currency;
import wallet.entity.Wallet;
import wallet.exception.InsufficientBalanceException;
import wallet.exception.InvalidInputException;
import wallet.model.CreateWalletRequest;
import wallet.model.InitiateTransactionRequest;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    private final Set<String> supportedCurrencies;

    @Autowired
    public ValidationService() {
        supportedCurrencies = Arrays.stream(Currency.values()).map(Currency::toString).collect(Collectors.toSet());
    }

    void validate(CreateWalletRequest request) throws InvalidInputException {
        validateCurrency(request.getCurrency(), request);
    }

    void validate(InitiateTransactionRequest request, Wallet sourceWallet, Wallet targetWallet) throws InvalidInputException, InsufficientBalanceException {
        validateCurrency(request.getCurrency(), request);
        Currency transactionCurrency = Currency.valueOf(request.getCurrency());
        if(transactionCurrency != sourceWallet.getCurrency() ||
            transactionCurrency != targetWallet.getCurrency()) {
            throw new InvalidInputException(String.format("Transaction currency %s does not match source currency %s or target currency %s",
                    transactionCurrency, sourceWallet.getCurrency(), targetWallet.getCurrency()));
        }
        if(sourceWallet.getBalanceAmount().subtract(request.getAmount()).compareTo(sourceWallet.getMinimumBalanceAmount()) < 0) {
            throw new InsufficientBalanceException(String.format("Could not transact transaction %s. Balance %s is not sufficient. Minimum balance %s",
                    request, sourceWallet.getBalanceAmount(), sourceWallet.getMinimumBalanceAmount()));
        }
    }

    private <T> void validateCurrency(String currency,T object) throws InvalidInputException {
        if(!supportedCurrencies.contains(currency)) {
            throw new InvalidInputException(String.format("Unknown currency %s in request %s", currency, object));
        }
    }


}
