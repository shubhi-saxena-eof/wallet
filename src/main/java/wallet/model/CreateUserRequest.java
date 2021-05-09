package wallet.model;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }
}
