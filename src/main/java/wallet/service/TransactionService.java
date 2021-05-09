package wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wallet.model.Currency;
import wallet.entity.Transaction;
import wallet.model.TransactionStatus;
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

    public Transaction transact(InitiateTransactionRequest request) throws InvalidInputException, InsufficientBalanceException {
        Transaction transaction = initiateTransaction(request);
        for(int tryCount = 1; true; tryCount++) {
            try {
                return executeTransaction(request, transaction);
            } catch (InvalidInputException | InsufficientBalanceException e) {
                markFailed(transaction);
                throw e;
            } catch (Exception e) {
                if(tryCount == maxTries) {
                    markFailed(transaction);
                    throw e;
                }
                log.error("Transaction request {} failed - retrying", request,e);
                markFailed(transaction);
            }
        }
    }

    private void markFailed(Transaction transaction) {
        if(transaction.getStatus() != TransactionStatus.FAIL) {
            transaction.setStatus(TransactionStatus.FAIL);
            transactionRepository.save(transaction);
        }
    }

    private Transaction markSuccess(Transaction transaction) {
        transaction.setStatus(TransactionStatus.SUCCESS);
        return transactionRepository.save(transaction);
    }

    @Transactional
    private Transaction executeTransaction(InitiateTransactionRequest request, Transaction transaction) throws InvalidInputException, InsufficientBalanceException {
        Wallet sourceWallet = getWallet(request.getSourceWalletID());
        Wallet targetWallet = getWallet(request.getTargetWalletID());
        validationService.validate(request, sourceWallet, targetWallet);
        debitSource(request, sourceWallet);
        creditTarget(request, targetWallet);
        return markSuccess(transaction);
    }

    private void creditTarget(InitiateTransactionRequest request, Wallet targetWallet) {
        targetWallet.setBalanceAmount(targetWallet.getBalanceAmount().add(request.getAmount()));
        walletRepository.save(targetWallet);
    }

    private void debitSource(InitiateTransactionRequest request, Wallet sourceWallet) {
        sourceWallet.setBalanceAmount(sourceWallet.getBalanceAmount().subtract(request.getAmount()));
        walletRepository.save(sourceWallet);
    }

    private Transaction initiateTransaction(InitiateTransactionRequest request) {
        Transaction transaction = getNewTransaction(request);
        return transactionRepository.save(transaction);
    }

    private Transaction getNewTransaction(InitiateTransactionRequest request) {
        return new Transaction(
                request.getAmount(),
                Currency.valueOf(request.getCurrency()),
                walletUtil.getCurrentTime(),
                request.getSourceWalletID(),
                request.getTargetWalletID(),
                TransactionStatus.PENDING
        );
    }


    private Wallet getWallet(UUID walletID) throws InvalidInputException {
        return walletRepository.findById(walletID)
                .orElseThrow(() -> new InvalidInputException(String.format("Input wallet with id %s does not exist", walletID)));
    }


}
