package lk.ijse.cleancopvt.controller;

import lk.ijse.cleancopvt.dto.PaymentDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.entity.Booking;
import lk.ijse.cleancopvt.service.BookingService;
import lk.ijse.cleancopvt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final ResponseDTO responseDTO;

    public BookingController(BookingService bookingService, JwtUtil jwtUtil, ResponseDTO responseDTO) {
        this.bookingService = bookingService;
        this.jwtUtil = jwtUtil;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/addBooking")
    public ResponseEntity<String> addBooking(@RequestBody Booking booking) {
        try {
            bookingService.createBooking(booking);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/updatePaymentStatus/{bookingId}")
    public ResponseEntity<String> updateBookingAfterPayment(@PathVariable("bookingId") UUID bookingId, @RequestBody PaymentDTO paymentDTO) {
        try {
            bookingService.updateBookingStatusAfterPayment(bookingId, paymentDTO);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/confirmBooking/{bookingId}")
    public ResponseEntity<String> confirmBooking(@PathVariable("bookingId") UUID bookingId) {
        try {
            bookingService.confirmBooking(bookingId);
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    @PutMapping("/rejectBooking/{bookingId}")
    public ResponseEntity<String> rejectBooking(@PathVariable("bookingId") UUID bookingId) {
        try {
            bookingService.rejectBooking(bookingId);
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
