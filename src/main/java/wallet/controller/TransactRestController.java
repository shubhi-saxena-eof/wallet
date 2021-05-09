package wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wallet.entity.Transaction;
import wallet.exception.InsufficientBalanceException;
import wallet.exception.InvalidInputException;
import wallet.exception.TransactAPIException;
import wallet.model.InitiateTransactionRequest;
import wallet.service.TransactionService;

import javax.validation.Valid;

@RestController
public class TransactRestController {

    private final TransactionService transactionService;

    @Autowired
    public TransactRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transact")
    private Transaction transact(
            @RequestBody @Valid InitiateTransactionRequest request) throws TransactAPIException {
        try {
            return transactionService.transact(request);
        } catch (InvalidInputException | InsufficientBalanceException e) {
            throw new TransactAPIException("Could not transact transaction " + request + e.getMessage(), e);
        }
    }

}
