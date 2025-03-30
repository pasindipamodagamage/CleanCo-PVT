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
    private lk.ijse.cleancopvt.Enum.ServiceType serviceType=ServiceType.GENERAL_CLEAN;

    private String description;
//    private String duration;
//    private double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "services")
    private List<Booking> bookings = new ArrayList<>();
}
