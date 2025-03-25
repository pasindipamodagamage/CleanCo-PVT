package lk.ijse.cleanco.Embedded;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Name {

//    @NotNull(message = "First Name cannot be null")
    private String firstName;

//    @NotNull(message = "Last Name cannot be null")
    private String lastName;

}

