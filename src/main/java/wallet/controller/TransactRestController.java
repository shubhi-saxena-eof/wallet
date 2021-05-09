package wallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.entity.Transaction;
import wallet.exception.InsufficientBalanceException;
import wallet.exception.InvalidInputException;
import wallet.exception.TransactAPIException;
import wallet.exception.UserAPIException;
import wallet.model.InitiateTransactionRequest;
import wallet.service.TransactionService;

import javax.validation.Valid;

@RestController
@Slf4j
public class TransactRestController {

    private final TransactionService transactionService;

    @Autowired
    public TransactRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transact")
    private ResponseEntity<Transaction> transact(
            @RequestBody @Valid InitiateTransactionRequest request) throws TransactAPIException {
        try {
            log.info("Received request to initiate transaction request {}", request);
            Transaction transaction = transactionService.transact(request);
            log.info("Transaction successfully complete {} for request {}", transaction, request);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (InvalidInputException | InsufficientBalanceException e) {
            log.error("Could not execute transaction {}", request, e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new TransactAPIException("Could not execute transaction " + request + e.getMessage(), e);
        }
    }

}
