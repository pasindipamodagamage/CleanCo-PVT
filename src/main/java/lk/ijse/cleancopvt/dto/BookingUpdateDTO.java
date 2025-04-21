package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingUpdateDTO {
    private UUID bookingID;
    private BookingStatus status;
}
