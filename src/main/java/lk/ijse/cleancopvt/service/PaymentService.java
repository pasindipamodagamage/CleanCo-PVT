package lk.ijse.cleancopvt.service;

import lk.ijse.cleancopvt.dto.PaymentDTO;
import lk.ijse.cleancopvt.entity.Payment;

import java.util.UUID;

public interface PaymentService {

    public Payment createPayment(PaymentDTO paymentDTO);
    public void updatePaymentStatus(UUID bookingId, PaymentDTO paymentDTO);
}
