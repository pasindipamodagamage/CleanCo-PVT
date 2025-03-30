package lk.ijse.cleancopvt.controller;

import lk.ijse.cleancopvt.dto.PaymentDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.entity.Booking;
import lk.ijse.cleancopvt.service.BookingService;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.ResponseUtil;
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
            return ResponseUtil.generateSuccessResponse("Booking created successfully, payment status is PENDING.");
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse("Error while creating booking: " + e.getMessage());
        }
    }

    @PutMapping("/updatePaymentStatus/{bookingId}")
    public ResponseEntity<String> updateBookingAfterPayment(@PathVariable("bookingId") UUID bookingId, @RequestBody PaymentDTO paymentDTO) {
        try {
            bookingService.updateBookingStatusAfterPayment(bookingId, paymentDTO);
            return ResponseUtil.generateSuccessResponse("Booking status updated successfully after payment.");
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse("Error while updating booking after payment: " + e.getMessage());
        }
    }

    @PutMapping("/confirmBooking/{bookingId}")
    public ResponseEntity<String> confirmBooking(@PathVariable("bookingId") UUID bookingId) {
        try {
            bookingService.confirmBooking(bookingId);
            return ResponseUtil.generateSuccessResponse("Booking confirmed successfully.");
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse("Error while confirming booking: " + e.getMessage());
        }
    }


    @PutMapping("/rejectBooking/{bookingId}")
    public ResponseEntity<String> rejectBooking(@PathVariable("bookingId") UUID bookingId) {
        try {
            bookingService.rejectBooking(bookingId);
            return ResponseUtil.generateSuccessResponse("Booking rejected successfully.");
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse("Error while rejecting booking: " + e.getMessage());
        }
    }
}
