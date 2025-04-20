package lk.ijse.cleancopvt.service;

import lk.ijse.cleancopvt.dto.BookingDTO;
import lk.ijse.cleancopvt.dto.PaymentDTO;

import java.util.UUID;

public interface BookingService {

    void createBooking(BookingDTO bookingDTO) throws Exception;

    void updateBookingStatusAfterPayment(UUID bookingId, PaymentDTO paymentDTO);

    void confirmBooking(UUID bookingId) throws Exception;

    void rejectBooking(UUID bookingId) throws Exception;

    void cancelBooking(UUID bookingId, UUID userId) throws Exception;

    void completeBooking(UUID bookingId) throws Exception;
}
