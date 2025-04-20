package lk.ijse.cleancopvt.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.dto.BookingDTO;
import lk.ijse.cleancopvt.dto.CategoryDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.service.impl.BookingServiceImpl;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.ResponseUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin("*")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final JwtUtil jwtUtil;
    private final ResponseDTO responseDTO;

    @Autowired
    public BookingController(BookingServiceImpl bookingService, JwtUtil jwtUtil, ResponseDTO responseDTO) {
        this.bookingService = bookingService;
        this.jwtUtil = jwtUtil;
        this.responseDTO = responseDTO;
    }

//    @PreAuthorize("hasRole('CUSTOMER')")
//    @PostMapping("/addBooking")
//    public ResponseEntity<?> addBooking(HttpServletRequest request, @RequestBody BookingDTO bookingDTO) {
//        String authorization = request.getHeader("Authorization");
//        if (!hasRequiredRole(authorization, Role.Customer)) {
//            return ResponseEntity.status(403).body(new ResponseUtil(403, "Forbidden: User not authorized", null));
//        }
//
//        // Get userId from the JWT token
//        String token = authorization.substring(7); // Remove "Bearer " prefix
//        Claims claims = jwtUtil.getUserRoleCodeFromToken(token);
//        UUID userId = UUID.fromString(claims.getSubject()); // Assuming userId is stored as subject in the token
//
//        // Set booking details
//        bookingDTO.setUserId(userId);
//        bookingDTO.setBookingDate(LocalDate.now()); // Use the current date as booking date
//        bookingDTO.setBookingTime(LocalTime.now()); // Use the current time as booking time
//        bookingDTO.setBookingStatus(BookingStatus.PENDING); // Set status to PENDING
//
//        // Call the service to save the booking
//        bookingService.addBooking(bookingDTO);
//
//        return ResponseEntity.ok(new ResponseUtil(200, "Booking Added Successfully", null));
//    }

//    @PostMapping("/addBooking")
//    public ResponseEntity<String> addCategory(@RequestHeader("Authorization") String authorization, @RequestBody BookingDTO bookingDTO) {
//        if (!hasRequiredRole(authorization, Role.Customer)) {
//            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
//        }
//
//        try {
//            bookingService.addBooking(bookingDTO);
//            return ResponseEntity.status(VarList.Created).body("Booking created successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(VarList.Internal_Server_Error).body("Failed Booking " + e.getMessage());
//        }
//    }

    @PostMapping("/addBooking")
    public ResponseEntity<ResponseUtil> addCategory(
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
