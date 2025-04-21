package lk.ijse.cleancopvt.controller;

import io.jsonwebtoken.Claims;
import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.dto.*;
import lk.ijse.cleancopvt.repo.BookingRepo;
import lk.ijse.cleancopvt.service.impl.BookingServiceImpl;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.ResponseUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin("*")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final JwtUtil jwtUtil;
    private final ResponseDTO responseDTO;
    private BookingRepo bookingRepo;

    @Autowired
    public BookingController(BookingServiceImpl bookingService, JwtUtil jwtUtil, ResponseDTO responseDTO) {
        this.bookingService = bookingService;
        this.jwtUtil = jwtUtil;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/addBooking")
    public ResponseEntity<ResponseUtil> addBooking(
            @RequestHeader("Authorization") String authorization,
            @RequestBody BookingDTO bookingDTO) {

        if (!hasRequiredRole(authorization, Role.Customer)) {
            return ResponseEntity.status(VarList.Forbidden)
                    .body(new ResponseUtil(VarList.Forbidden, "Access denied: You do not have the required role.", null));
        }

        try {
            bookingService.addBooking(bookingDTO);
            return ResponseEntity.status(VarList.Created)
                    .body(new ResponseUtil(VarList.Created, "Booking created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error)
                    .body(new ResponseUtil(VarList.Internal_Server_Error, "Failed Booking: " + e.getMessage(), null));
        }
    }

    @PutMapping("/confirmBooking/{id}")
    public ResponseEntity<String> confirmBooking(@RequestHeader("Authorization") String authorization,
                                                 @PathVariable("id") UUID id, @RequestBody BookingUpdateDTO bookingUpdateDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            bookingUpdateDTO.setBookingID(id);
            bookingService.updateBooking(bookingUpdateDTO);
            return ResponseEntity.status(VarList.OK).body("Booking is confirmed");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error updating: " + e.getMessage());
        }
    }

    @PutMapping("/rejectBooking/{id}")
    public ResponseEntity<String> rejectBooking(@RequestHeader("Authorization") String authorization,
                                                @PathVariable("id") UUID id, @RequestBody BookingUpdateDTO bookingUpdateDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            bookingUpdateDTO.setBookingID(id);
            bookingService.updateBooking(bookingUpdateDTO);
            return ResponseEntity.status(VarList.OK).body("Booking is rejected");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error updating: " + e.getMessage());
        }
    }

    @PutMapping("/cancelBooking/{id}")
    public ResponseEntity<String> cancelBooking(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id, @RequestBody BookingUpdateDTO bookingUpdateDTO) {
        if (!hasRequiredRole(authorization, Role.Customer)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            bookingUpdateDTO.setBookingID(id);
            bookingService.updateBooking(bookingUpdateDTO);
            return ResponseEntity.status(VarList.OK).body("Booking is cancelled");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error updating: " + e.getMessage());
        }
    }

    @PutMapping("/completedBooking/{id}")
    public ResponseEntity<String> completedBooking(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id, @RequestBody BookingUpdateDTO bookingUpdateDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            bookingUpdateDTO.setBookingID(id);
            bookingService.updateBooking(bookingUpdateDTO);
            return ResponseEntity.status(VarList.OK).body("Booking is completed");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error updating: " + e.getMessage());
        }
    }


    @GetMapping("/getAllBookings")
    public ResponseEntity<List<BookingDTO>> getAllCategories(@RequestHeader("Authorization") String authorization) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee,Role.Customer)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
            return ResponseEntity.status(VarList.OK).body(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
        }
    }

    @GetMapping("/getAllPendingBookings")
    public ResponseEntity<List<PendingBooking>> getAllPendingBookings(@RequestHeader("Authorization") String authorization) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            List<PendingBooking> bookings = bookingService.getAllPendingBookings();
            return ResponseEntity.status(VarList.OK).body(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
        }
    }

    @GetMapping("/getBookingsForUser")
    public ResponseEntity<List<BookingCustomerTM>> getBookingsForUser(@RequestHeader("Authorization") String authorization,
                                                               @RequestParam UUID userId) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee, Role.Customer)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            List<BookingCustomerTM> bookings = bookingService.getBookingsForUser(userId);
            return ResponseEntity.status(VarList.OK).body(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
        }
    }

    @GetMapping("/countPendingBookings")
    public ResponseEntity<ResponseUtil> getPendingBookingsCount() {
        try {
            long pendingBookingsCount = bookingService.countPendingBookings();
            return ResponseEntity.ok(new ResponseUtil(VarList.OK, "Pending bookings count retrieved successfully", pendingBookingsCount));
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error)
                    .body(new ResponseUtil(VarList.Internal_Server_Error, "Failed to retrieve pending bookings count: " + e.getMessage(), null));
        }
    }

    public long countPendingBookings() {
        return bookingRepo.countByBookingStatus(BookingStatus.PENDING);
    }


    private boolean hasRequiredRole(String authorization, Role... roles) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return false;
        }

        String token = authorization.substring(7);
        Claims claims = jwtUtil.getUserRoleCodeFromToken(token);
        String userRole = claims.get("role", String.class);

        for (Role role : roles) {
            if (role.name().equalsIgnoreCase(userRole)) {
                return true;
            }
        }
        return false;
    }
}