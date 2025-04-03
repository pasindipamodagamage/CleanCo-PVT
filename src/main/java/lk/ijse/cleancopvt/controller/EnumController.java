package lk.ijse.cleancopvt.controller;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.Enum.District;
import lk.ijse.cleancopvt.Enum.PaymentStatus;
import lk.ijse.cleancopvt.Enum.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/districts")
    public List<District> getDistricts() {
        return Arrays.asList(District.values());
    }

    @GetMapping("/bookingStatus")
    public List<District> getBookingStatus(BookingStatus bookingStatus) {
        return Arrays.asList(District.values());
    }

    @GetMapping("/paymentStatus")
    public List<District> getPaymentStatus(PaymentStatus paymentStatus) {
        return Arrays.asList(District.values());
    }

    @GetMapping("/rating")
    public List<District> getRating() {
        return Arrays.asList(District.values());
    }

    @GetMapping("/serviceType")
    public List<District> getServiceType() {
        return Arrays.asList(District.values());
    }
}
