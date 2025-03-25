package lk.ijse.cleancopvt.entity;

import jakarta.persistence.*;
import lk.ijse.cleancopvt.Enum.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @OneToOne
//    @JoinColumn(name = "booking_id", nullable = false)
//    private Booking booking;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false, unique = true)
    private Payment payment;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    @Enumerated(EnumType.STRING)
    private Rating rating;
    private String comment;
}