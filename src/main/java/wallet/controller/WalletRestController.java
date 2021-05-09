package wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wallet.entity.Transaction;
import wallet.entity.Wallet;
import wallet.exception.InvalidInputException;
import wallet.exception.WalletAPIException;
import wallet.model.CreateWalletRequest;
import wallet.model.InitiateTransactionRequest;
import wallet.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletRestController {

    private final WalletService walletService;

    @Autowired
    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(path = "/create")
    private Wallet createWallet(
            @RequestParam UUID user,
            @RequestBody CreateWalletRequest request) throws WalletAPIException {
        try {
            return walletService.createWallet(user, request);
        } catch (InvalidInputException e) {
            throw new WalletAPIException("Could not create user for request " + request + e.getMessage(), e);
        }
    }

    @PostMapping(path = "/transact")
    private Transaction transact(
            @RequestParam UUID wallet,
            @RequestBody InitiateTransactionRequest request) {
        return new Transaction(); //TODO implement
    }

    @GetMapping(path = "/balance")
    private BigDecimal getBalance(
            @RequestParam UUID wallet) throws WalletAPIException {
        try {
            return walletService.getWalletBalance(wallet);
        } catch (InvalidInputException e) {
            throw new WalletAPIException("Could not get balance for wallet id " + wallet + e.getMessage(), e);
        }
    }

}
