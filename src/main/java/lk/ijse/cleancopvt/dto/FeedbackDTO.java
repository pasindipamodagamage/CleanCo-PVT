package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackDTO {

    private int id;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Rating rating;
    private String comment;
}
