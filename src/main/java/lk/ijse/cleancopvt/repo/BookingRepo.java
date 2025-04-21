package lk.ijse.cleancopvt.repo;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepo extends JpaRepository<Booking, UUID> {
    long countByBookingStatus(BookingStatus bookingStatus);
    List<Booking> findByUserId(UUID userId);
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}