package lk.ijse.cleancopvt.repo;

import lk.ijse.cleancopvt.entity.ServicesSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepo extends JpaRepository<ServicesSet, UUID> {
}
