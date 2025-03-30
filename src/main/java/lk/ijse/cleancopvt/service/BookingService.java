package lk.ijse.cleancopvt.service;

import lk.ijse.cleancopvt.dto.PaymentDTO;
import lk.ijse.cleancopvt.entity.Booking;

import java.util.UUID;

public interface BookingService {
    public Booking createBooking(Booking booking);
    public void updateBookingStatusAfterPayment(UUID bookingId, PaymentDTO paymentDTO);
    public void confirmBooking(UUID bookingId);
    public void rejectBooking(UUID bookingId);
}
