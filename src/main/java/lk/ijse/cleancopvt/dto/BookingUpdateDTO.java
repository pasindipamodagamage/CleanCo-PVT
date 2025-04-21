package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingUpdateDTO {
    private UUID bookingId;
    private UUID userId;
    private UUID categoryId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private BookingStatus bookingStatus;
}

