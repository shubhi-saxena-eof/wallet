package wallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.entity.Wallet;
import wallet.exception.InvalidInputException;
import wallet.exception.UserAPIException;
import wallet.exception.WalletAPIException;
import wallet.model.CreateWalletRequest;
import wallet.service.WalletService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletRestController {

    private final WalletService walletService;

    @Autowired
    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(path = "/create")
    private ResponseEntity<Wallet> createWallet(
            @RequestParam @NotBlank UUID userID,
            @RequestBody @Valid CreateWalletRequest request) throws WalletAPIException {
        try {
            log.info("Received create wallet request {}", request);
            Wallet wallet = walletService.createWallet(userID, request);
            log.info("Create wallet request complete {}, wallet {}", request, wallet);
            return ResponseEntity.ok(wallet);
        } catch (InvalidInputException e) {
            log.error("Could not create wallet for request {}", request, e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new WalletAPIException("Could not create wallet for request " + request + e.getMessage(), e);
        }
    }

    @GetMapping(path = "/balance")
    private ResponseEntity<BigDecimal> getBalance(
            @RequestParam @NotBlank UUID walletID) throws WalletAPIException {
        try {
            log.info("Received get balance request for wallet {}", walletID);
            BigDecimal balance = walletService.getWalletBalance(walletID);
            log.info("Get balance request for wallet {} complete {}", walletID, balance);
            return ResponseEntity.ok(balance);
        } catch (InvalidInputException e) {
            log.error("Could not get balance for walletID {}", walletID, e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new WalletAPIException("Could not get balance for walletID {}" + walletID , e);
        }
    }

}
