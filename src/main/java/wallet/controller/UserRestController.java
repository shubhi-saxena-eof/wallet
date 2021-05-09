package wallet.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.entity.User;
import wallet.model.CreateUserRequest;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @PostMapping(path = "/create")
    private User createWallet(
            @RequestBody CreateUserRequest request) {
        return new User(); //TODO implement
    }

}
