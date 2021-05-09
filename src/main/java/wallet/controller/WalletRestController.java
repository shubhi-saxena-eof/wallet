package wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wallet.entity.Wallet;
import wallet.exception.InvalidInputException;
import wallet.exception.WalletAPIException;
import wallet.model.CreateWalletRequest;
import wallet.service.WalletService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
            @RequestParam @NotBlank UUID userID,
            @RequestBody @Valid CreateWalletRequest request) throws WalletAPIException {
        try {
            return walletService.createWallet(userID, request);
        } catch (InvalidInputException e) {
            throw new WalletAPIException("Could not create wallet for request " + request + e.getMessage(), e);
        }
    }

    @GetMapping(path = "/balance")
    private BigDecimal getBalance(
            @RequestParam @NotBlank UUID walletID) throws WalletAPIException {
        try {
            return walletService.getWalletBalance(walletID);
        } catch (InvalidInputException e) {
            throw new WalletAPIException("Could not get balance for wallet id " + walletID + e.getMessage(), e);
        }
    }

}
