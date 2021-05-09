package wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.entity.User;
import wallet.exception.InvalidInputException;
import wallet.model.CreateUserRequest;
import wallet.repository.UserRepository;

@Service
public class UserService {

    private final ValidationService validationService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ValidationService validationService, UserRepository userRepository) {
        this.validationService = validationService;
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) throws InvalidInputException {
        validationService.validate(CreateUserRequest.class, request);
        User user = getNewUser(request);
        return userRepository.save(user);
    }

    private User getNewUser(CreateUserRequest request) {
        return new User(request.getPhoneNumber(), request.getName());
    }
}
