package lk.ijse.cleancopvt.repo;

import lk.ijse.cleancopvt.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    Payment findByBooking_Id(UUID bookingId);

}
