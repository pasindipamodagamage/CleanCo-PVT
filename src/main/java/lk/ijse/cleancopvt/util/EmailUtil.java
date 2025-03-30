package lk.ijse.cleancopvt.util;

import lk.ijse.cleancopvt.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    // Send booking confirmation email
    public void sendBookingConfirmationEmail(String customerEmail, Booking booking) {
        // Logic to send email
        String subject = "Booking Confirmation";
        String message = "Dear Customer, your booking has been confirmed. Booking ID: " + booking.getId();
        sendEmail(customerEmail, subject, message);
    }

    // Send booking rejection email
    public void sendBookingRejectionEmail(String customerEmail, Booking booking) {
        // Logic to send email
        String subject = "Booking Rejection";
        String message = "Dear Customer, your booking has been rejected. Booking ID: " + booking.getId();
        sendEmail(customerEmail, subject, message);
    }

    // Generic method to send email
    private void sendEmail(String to, String subject, String message) {
        // Implement email sending logic (e.g., using JavaMailSender)
        System.out.println("Email sent to: " + to + " with subject: " + subject + " and message: " + message);
    }
}
