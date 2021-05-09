package wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wallet.entity.User;
import wallet.exception.UserAPIException;
import wallet.model.CreateUserRequest;
import wallet.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    private @ResponseBody User createWallet(
            @RequestBody @Valid CreateUserRequest request) throws UserAPIException {
        try {
            return userService.createUser(request);
        } catch (Exception e) {
            throw new UserAPIException("Could not create user for request " + request + e.getMessage(), e);
        }
    }

}
