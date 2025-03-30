package lk.ijse.cleancopvt.repo;

import lk.ijse.cleancopvt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepo extends JpaRepository<User, UUID> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

}