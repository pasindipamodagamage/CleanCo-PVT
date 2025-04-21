package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.dto.BookingDTO;
import lk.ijse.cleancopvt.dto.BookingUpdateDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepo.findAll();
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

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

    public void updateBooking(BookingUpdateDTO bookingUpdateDTO) {
        Booking booking = modelMapper.map(bookingUpdateDTO, Booking.class);
        bookingRepo.save(booking);
    }

    public long countPendingBookings() {
        return bookingRepo.countByBookingStatus(BookingStatus.PENDING);
    }



}
