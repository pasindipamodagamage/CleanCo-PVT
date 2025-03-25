package lk.ijse.cleancopvt.Embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lk.ijse.cleancopvt.Enum.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Address {
    private String locationNumber;
    private String street;
    private String city;
    @Enumerated
    private District district;
}
