package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.dto.*;
import lk.ijse.cleancopvt.entity.Booking;
import lk.ijse.cleancopvt.entity.Category;
import lk.ijse.cleancopvt.entity.User;
import lk.ijse.cleancopvt.repo.BookingRepo;
import lk.ijse.cleancopvt.repo.CategoryRepo;
import lk.ijse.cleancopvt.repo.UserRepo;
import lk.ijse.cleancopvt.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

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

    public List<PendingBookingTM> getAllPendingBookings() {
        List<Booking> pendingBookings = bookingRepo.findByBookingStatus(BookingStatus.PENDING);

        return pendingBookings.stream()
                .map(booking -> new PendingBookingTM(
                        booking.getId(),
                        booking.getUser().getName(),
                        booking.getCategory().getName(),
                        booking.getUser().getPrimaryContact(),
                        booking.getBookingDate(),
                        booking.getBookingTime()
                ))
                .collect(Collectors.toList());
    }

    public List<RejectBookingTM> getAllRejectBookings() {
        List<Booking> rejectBooking = bookingRepo.findByBookingStatus(BookingStatus.REJECTED);

        return rejectBooking.stream()
                .map(booking -> new RejectBookingTM(
                        booking.getId(),
                        booking.getUser().getName(),
                        booking.getCategory().getName(),
                        booking.getBookingDate(),
                        booking.getBookingTime()
                ))
                .collect(Collectors.toList());
    }
    public List<BookingDTO> getBookingsByStatus(BookingStatus status) {
        List<Booking> bookings = bookingRepo.findByBookingStatus(status);
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookingCustomerTM> getBookingsForUser(UUID userId) {
        List<Booking> bookings = bookingRepo.findByUserId(userId);

        return bookings.stream().map(booking -> {
            BookingCustomerTM dto = new BookingCustomerTM();
            dto.setUserid(booking.getUser().getId());
            dto.setBookingDate(booking.getBookingDate());
            dto.setBookingStatus(booking.getBookingStatus());
            dto.setCategoryName(booking.getCategory().getName());
            return dto;
        }).collect(Collectors.toList());
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

    public void updateBooking(BookingUpdateDTO dto) {
        Booking booking = bookingRepo.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setBookingStatus(dto.getBookingStatus());
        bookingRepo.save(booking);
    }

    public long countPendingBookings() {
        return bookingRepo.countByBookingStatus(BookingStatus.PENDING);
    }

}