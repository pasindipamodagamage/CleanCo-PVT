package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingCustomerTM {
    private UUID userid;
    private LocalDate bookingDate;
    private String categoryName;
    private BookingStatus bookingStatus;
}
