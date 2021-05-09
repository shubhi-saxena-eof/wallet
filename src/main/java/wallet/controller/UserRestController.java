package wallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.entity.User;
import wallet.exception.InvalidInputException;
import wallet.exception.UserAPIException;
import wallet.model.CreateUserRequest;
import wallet.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    private @ResponseBody ResponseEntity<User> createWallet(
            @RequestBody @Valid CreateUserRequest request) throws UserAPIException {
        try {
            log.info("Received create user request ");
            User user = userService.createUser(request);
            log.info("Create user request complete");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new UserAPIException("Could not create user for request " + request + e.getMessage(), e);
        }
    }

}
