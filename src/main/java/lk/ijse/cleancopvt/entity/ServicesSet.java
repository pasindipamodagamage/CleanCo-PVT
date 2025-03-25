package lk.ijse.cleanco.Entity;

import jakarta.persistence.*;
import lk.ijse.cleanco.Enum.ServiceType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ServicesSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String serviceName;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType=ServiceType.GENERAL_CLEAN;

    private String description;
//    private String duration;
//    private double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "services")
    private List<Booking> bookings = new ArrayList<>();
}
