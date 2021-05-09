package wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wallet.entity.Currency;
import wallet.entity.Transaction;
import wallet.entity.TransactionStatus;
import wallet.entity.Wallet;
import wallet.exception.InsufficientBalanceException;
import wallet.exception.InvalidInputException;
import wallet.exception.TransactAPIException;
import wallet.model.InitiateTransactionRequest;
import wallet.repository.TransactionRepository;
import wallet.repository.WalletRepository;
import wallet.util.WalletUtil;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {

    private final ValidationService validationService;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final WalletUtil walletUtil;
    @Value("${wallet.transaction.maxretries}")
    private int maxTries;

    public TransactionService(ValidationService validationService, WalletRepository walletRepository, TransactionRepository transactionRepository, WalletUtil walletUtil) {
        this.validationService = validationService;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.walletUtil = walletUtil;
    }

    @Transactional
    public Transaction transact(InitiateTransactionRequest request) throws TransactAPIException, InvalidInputException, InsufficientBalanceException {

        Wallet sourceWallet = getWallet(request.getSourceWalletID());
        Wallet targetWallet = getWallet(request.getTargetWalletID());
        validationService.validate(request, sourceWallet, targetWallet);
        Transaction transaction = initiateTransaction(request, sourceWallet, targetWallet);
        return executeTransaction(transaction, request, sourceWallet, targetWallet);
    }

    private void markFailed(Transaction transaction) {
        if(transaction.getStatus() != TransactionStatus.FAIL) {
            transaction.setStatus(TransactionStatus.FAIL);
            transactionRepository.save(transaction);
        }
    }

    private Transaction markSuccess(Transaction transaction) {
        transaction.setSettlementDateTime(walletUtil.getCurrentTime());
        transaction.setStatus(TransactionStatus.SUCCESS);
        return transactionRepository.save(transaction);
    }

    private Transaction executeTransaction(Transaction transaction, InitiateTransactionRequest request, Wallet sourceWallet, Wallet targetWallet) {
        for(int tryCount = 1; true; tryCount++) {
            try {
                debitSource(request, sourceWallet);
                creditTarget(request, targetWallet);
                return markSuccess(transaction);
            } catch (Exception e) {
                if(tryCount == maxTries) {
                    throw e;
                }
                //TODO log error
                markFailed(transaction);
            }
        }
    }

    private void creditTarget(InitiateTransactionRequest request, Wallet targetWallet) {
        targetWallet.setBalanceAmount(targetWallet.getBalanceAmount().add(request.getAmount()));
        walletRepository.save(targetWallet);
    }

    private void debitSource(InitiateTransactionRequest request, Wallet sourceWallet) {
        sourceWallet.setBalanceAmount(sourceWallet.getBalanceAmount().subtract(request.getAmount()));
        walletRepository.save(sourceWallet);
    }

    private Transaction initiateTransaction(InitiateTransactionRequest request, Wallet sourceWallet, Wallet targetWallet) {
        Transaction transaction = getNewTransaction(request, sourceWallet, targetWallet);
        return transactionRepository.save(transaction);
    }

    private Transaction getNewTransaction(InitiateTransactionRequest request, Wallet sourceWallet, Wallet targetWallet) {
        return new Transaction(
                request.getAmount(),
                Currency.valueOf(request.getCurrency()),
                walletUtil.getCurrentTime(),
                sourceWallet,
                targetWallet,
                TransactionStatus.PENDING
        );
    }


    private Wallet getWallet(UUID walletID) throws InvalidInputException {
        return walletRepository.findById(walletID)
                .orElseThrow(() -> new InvalidInputException(String.format("Input wallet with id %s does not exist", walletID)));
    }


}
