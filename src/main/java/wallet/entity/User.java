package wallet.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String Name;
    //We can have additional fields like country code, address, ID proofs etc.


    public User(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        Name = name;
    }

}
