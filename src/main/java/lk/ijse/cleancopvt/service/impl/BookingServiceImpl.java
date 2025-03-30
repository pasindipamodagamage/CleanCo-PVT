package lk.ijse.cleancopvt.service.impl;


import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.Enum.PaymentStatus;
import lk.ijse.cleancopvt.dto.PaymentDTO;
import lk.ijse.cleancopvt.entity.Booking;
import lk.ijse.cleancopvt.entity.Payment;
import lk.ijse.cleancopvt.repo.BookingRepo;
import lk.ijse.cleancopvt.repo.PaymentRepo;
import lk.ijse.cleancopvt.service.BookingService;
import lk.ijse.cleancopvt.service.PaymentService;
import lk.ijse.cleancopvt.util.EmailUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private  BookingRepo bookingRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailUtil emailUtil;

    // Method to create a new booking and payment
    @Override
    public Booking createBooking(Booking booking) {
        bookingRepo.save(booking);

        // Create a payment associated with this booking
        Payment payment = new Payment();
//        payment.setBooking(booking);
        payment.setAmount(booking.getPayment() != null ? booking.getPayment().getAmount() : 0.0); // Set amount from payment
        payment.setPaymentStatus(PaymentStatus.PENDING);
        paymentRepo.save(payment);

        return booking;
    }

    // Method to update the booking and payment status after payment is made
    @Override
    public void updateBookingStatusAfterPayment(UUID bookingId, PaymentDTO paymentDTO) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking != null) {
            // Update payment status to PAID
            Payment payment = paymentRepo.findByBooking_Id(bookingId);
            if (payment != null) {
                payment.setPaymentStatus(PaymentStatus.PAID);
                payment.setAmount(paymentDTO.getAmount());
                payment.setPaymentDate(paymentDTO.getPaymentDate());
                paymentRepo.save(payment);
            }

            // Update booking status to CONFIRMED after payment
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepo.save(booking);

            // Send confirmation email to the customer
//            emailUtil.sendBookingConfirmationEmail(booking.getUser().getEmail(), booking);
        }
    }

    // Method to confirm a booking by an employee or administrator
    @Override
    public void confirmBooking(UUID bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepo.save(booking);

            // Send confirmation email to the customer
            emailUtil.sendBookingConfirmationEmail(booking.getUser().getEmail(), booking);
        }
    }

    // Method to reject a booking by an employee or administrator
    @Override
    public void rejectBooking(UUID bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setBookingStatus(BookingStatus.REJECTED);
            bookingRepo.save(booking);

            // Send rejection email to the customer
            emailUtil.sendBookingRejectionEmail(booking.getUser().getEmail(), booking);
        }
    }
}
