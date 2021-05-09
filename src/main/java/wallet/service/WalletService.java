package wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.entity.Currency;
import wallet.entity.User;
import wallet.entity.Wallet;
import wallet.exception.InvalidInputException;
import wallet.model.CreateWalletRequest;
import wallet.repository.UserRepository;
import wallet.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final ValidationService validationService;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(ValidationService validationService, UserRepository userRepository, WalletRepository walletRepository) {
        this.validationService = validationService;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(UUID userID, CreateWalletRequest request) throws InvalidInputException {
        validationService.validate(request);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new InvalidInputException(String.format("Input user with id %s does not exist", userID)));
        Wallet wallet = getNewWallet(user, request);
        return walletRepository.save(wallet);
    }

    public BigDecimal getWalletBalance(UUID walletID) throws InvalidInputException {
        Wallet wallet = walletRepository.findById(walletID)
                .orElseThrow(() -> new InvalidInputException(String.format("Input wallet with id %s does not exist", walletID)));
        return wallet.getBalanceAmount();
    }


    private Wallet getNewWallet(User user, CreateWalletRequest request) {
        return new Wallet(
            BigDecimal.ZERO,
            request.getMinimumBalanceAmount(),
            Currency.valueOf(request.getCurrency()),
            user
        );
    }

}
