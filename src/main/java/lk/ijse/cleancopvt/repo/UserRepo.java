package lk.ijse.cleancopvt.repo;

import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
//    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
//    long countCustomer(@Param("role") Role role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = ?1")
    long countCustomer(Role role);

    User findByEmail(String userName);
    boolean existsByEmail(String userName);
    int deleteByEmail(String userName);
    User findByNicNumber(String nicNumber);

}