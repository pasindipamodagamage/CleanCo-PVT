package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Embedded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PendingBooking {
    private UUID id;
    private Name name;
    private String categoryName;
    private String customerContact;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
}
