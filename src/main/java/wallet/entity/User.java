package wallet.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String phoneNumber;
    private String Name;
    //We can have additional fields like country code, address, ID proofs etc.


    public User(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        Name = name;
    }

}
