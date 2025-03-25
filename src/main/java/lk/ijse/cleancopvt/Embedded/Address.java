package lk.ijse.cleanco.Embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lk.ijse.cleanco.Enum.District;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Address {

//    @NotNull(message = "Location number cannot be null")
//    @Size(min = 1, max = 10, message = "Location number should be between 1 and 10 characters")
    private String locationNumber;

//    @NotNull(message = "Street cannot be null")
//    @Size(min = 1, max = 100, message = "Street name should be between 1 and 100 characters")
    private String street;

//    @NotNull(message = "City cannot be null")
//    @Size(min = 1, max = 50, message = "City name should be between 1 and 50 characters")
    private String city;

    @Enumerated
//    @NotNull(message = "District must be need")
    private District district;
}
