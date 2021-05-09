package wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.model.Currency;
import wallet.entity.Wallet;
import wallet.exception.InsufficientBalanceException;
import wallet.exception.InvalidInputException;
import wallet.model.CreateUserRequest;
import wallet.model.CreateWalletRequest;
import wallet.model.InitiateTransactionRequest;
import wallet.repository.UserRepository;
import wallet.repository.WalletRepository;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ValidationService {

    private final Set<String> supportedCurrencies;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public ValidationService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        supportedCurrencies = Arrays.stream(Currency.values()).map(Currency::toString).collect(Collectors.toSet());
    }

    void validate(CreateWalletRequest request, UUID userID) throws InvalidInputException {
        validateCurrency(request.getCurrency(), request);
        if(request.getInitialBalanceAmount().compareTo(request.getMinimumBalanceAmount()) < 0) {
            throw new InvalidInputException(String.format("Initial balance amount %s is less than minimmum balance amount %s",
                    request.getInitialBalanceAmount(), request.getMinimumBalanceAmount()));
        }
        if(walletRepository.findByUserId(userID).isPresent()) {
            throw new InvalidInputException(String.format("Wallet for user %s already exists.", userID));
        }
        log.info("Validated wallet creation request {}", request);
    }

    void validate(InitiateTransactionRequest request, Wallet sourceWallet, Wallet targetWallet) throws InvalidInputException, InsufficientBalanceException {
        validateCurrency(request.getCurrency(), request);
        Currency transactionCurrency = Currency.valueOf(request.getCurrency());
        if(transactionCurrency != sourceWallet.getCurrency() ||
            transactionCurrency != targetWallet.getCurrency()) {
            throw new InvalidInputException(String.format("Transaction currency %s does not match source currency %s or target currency %s",
                    transactionCurrency, sourceWallet.getCurrency(), targetWallet.getCurrency()));
        }
        if(sourceWallet.getID() == targetWallet.getID()) {
            throw new InvalidInputException(String.format("Transaction source and target are the same %s",
                    targetWallet.getID()));
        }
        if(sourceWallet.getBalanceAmount().subtract(request.getAmount()).compareTo(sourceWallet.getMinimumBalanceAmount()) < 0) {
            throw new InsufficientBalanceException(String.format("Could not transact transaction %s. Balance %s is not sufficient. Minimum balance %s",
                    request, sourceWallet.getBalanceAmount(), sourceWallet.getMinimumBalanceAmount()));
        }
        log.info("Validated transaction request {}", request);
    }

    private <T> void validateCurrency(String currency,T object) throws InvalidInputException {
        if(!supportedCurrencies.contains(currency)) {
            throw new InvalidInputException(String.format("Unknown currency %s in request %s", currency, object));
        }
        log.info("Validated currency for request {}", object);
    }


    void validate(CreateUserRequest request) throws InvalidInputException {
        if(userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new InvalidInputException("User with phone number already exists.");
        }
        log.info("Validated user creation request");
    }
}
