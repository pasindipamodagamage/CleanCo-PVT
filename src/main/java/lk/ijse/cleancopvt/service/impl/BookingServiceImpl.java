package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.dto.BookingDTO;
import lk.ijse.cleancopvt.dto.CategoryDTO;
import lk.ijse.cleancopvt.entity.Booking;
import lk.ijse.cleancopvt.entity.Category;
import lk.ijse.cleancopvt.entity.User;
import lk.ijse.cleancopvt.repo.BookingRepo;
import lk.ijse.cleancopvt.repo.CategoryRepo;
import lk.ijse.cleancopvt.repo.UserRepo;
import lk.ijse.cleancopvt.service.BookingService;
import lk.ijse.cleancopvt.util.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookingServiceImpl {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

//    public ResponseUtil addBooking(BookingDTO bookingDTO) {
//        // Create a new Booking object from the DTO
//        Booking booking = new Booking();
//        booking.setId(UUID.randomUUID()); // Generate a new unique ID for the booking
//        booking.setBookingDate(bookingDTO.getBookingDate());
//        booking.setBookingTime(bookingDTO.getBookingTime());
//        booking.setBookingStatus(BookingStatus.PENDING);
//
//        // Fetch User by userId
//        User user = userRepo.findById(bookingDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        booking.setUser(user);
//
//        // Fetch Category by categoryId
//        Category category = categoryRepo.findById(bookingDTO.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//        booking.setCategory(category);
//
//        // Save the booking
//        bookingRepo.save(booking);
//
//        return new ResponseUtil(200, "Booking Added Successfully", null);
//    }

//    @Override
public void addBooking(BookingDTO bookingDTO) {
    Booking booking = new Booking();
    booking.setBookingDate(bookingDTO.getBookingDate());
    booking.setBookingTime(bookingDTO.getBookingTime());
    booking.setBookingStatus(bookingDTO.getBookingStatus());

    User user = userRepo.findById(bookingDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
    Category category = categoryRepo.findById(bookingDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

    booking.setUser(user);
    booking.setCategory(category);

    bookingRepo.save(booking);
}

}
