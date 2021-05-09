package wallet.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String name;

}
