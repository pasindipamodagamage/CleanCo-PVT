package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private int id;

    private double amount;

    private LocalDate paymentDate;

    private PaymentStatus paymentStatus;
}
