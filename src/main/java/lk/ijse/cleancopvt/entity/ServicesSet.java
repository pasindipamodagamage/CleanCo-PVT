package lk.ijse.cleancopvt.entity;

import jakarta.persistence.*;
import lk.ijse.cleancopvt.Enum.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicesSet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String serviceName;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType = ServiceType.GENERAL_CLEAN;
    private String description;
    private double unitPrice;

    @ManyToMany
    @JoinTable(
            name = "service_category",  // Join table name
            joinColumns = @JoinColumn(name = "service_set_id"), // FK for ServicesSet
            inverseJoinColumns = @JoinColumn(name = "category_id")  // FK for Category
    )
    private List<Category> categories = new ArrayList<>();

}
