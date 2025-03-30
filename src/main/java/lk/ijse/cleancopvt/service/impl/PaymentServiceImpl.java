package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.Enum.PaymentStatus;
import lk.ijse.cleancopvt.dto.PaymentDTO;
import lk.ijse.cleancopvt.entity.Payment;
import lk.ijse.cleancopvt.repo.PaymentRepo;
import lk.ijse.cleancopvt.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    // Method to create a new payment (for use when booking is created)
    @Override
    public Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        paymentRepo.save(payment);
        return payment;
    }

    // Method to update the payment status to PAID after payment is made
    @Override
    public void updatePaymentStatus(UUID bookingId, PaymentDTO paymentDTO) {
        Payment payment = paymentRepo.findById(bookingId).orElse(null);
        if (payment != null) {
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setPaymentStatus(PaymentStatus.PAID);
            paymentRepo.save(payment);
        }
    }
}
