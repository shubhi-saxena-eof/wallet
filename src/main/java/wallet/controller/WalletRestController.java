package wallet.controller;

import org.springframework.web.bind.annotation.*;
import wallet.entity.Transaction;
import wallet.entity.Wallet;
import wallet.model.CreateWalletRequest;
import wallet.model.InitiateTransactionRequest;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletRestController {

    @PostMapping(path = "/create")
    private Wallet createWallet(
            @RequestParam UUID user,
            @RequestBody CreateWalletRequest request) {
        return new Wallet(); //TODO implement
    }

    @PostMapping(path = "/transact")
    private Transaction transact(
            @RequestParam UUID wallet,
            @RequestBody InitiateTransactionRequest request) {
        return new Transaction(); //TODO implement
    }

    @GetMapping(path = "/balance")
    private Wallet getBalance(
            @RequestParam UUID wallet) {
        return new Wallet(); //TODO implement
    }

}
