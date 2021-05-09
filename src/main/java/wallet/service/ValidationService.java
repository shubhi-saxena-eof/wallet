package wallet.service;

import org.springframework.stereotype.Service;
import wallet.entity.Currency;
import wallet.exception.InvalidInputException;
import wallet.model.CreateUserRequest;
import wallet.model.CreateWalletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    private final Validator validator;
    private final Set<String> supportedCurrencies;

    public ValidationService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        supportedCurrencies = Arrays.stream(Currency.values()).map(Currency::toString).collect(Collectors.toSet());
    }


    <T> void validate(Class<T> objectClass, T object) throws InvalidInputException {
        Set<ConstraintViolation<T>> violations = validator.validate(object, objectClass);
        throw new InvalidInputException(String.format("Invalidate object %s. Violations: %s",
                object, violations));
    }

    void validate(CreateWalletRequest request) throws InvalidInputException {
        validate(CreateWalletRequest.class, request);
        validateCurrency(request.getCurrency(), request);
    }

    private <T> void validateCurrency(String currency,T object) throws InvalidInputException {
        if(!supportedCurrencies.contains(currency)) {
            throw new InvalidInputException(String.format("Unknown currency %s in request %s",
                    currency, object));
        }
    }


}
