package lk.ijse.cleancopvt.entity;

import jakarta.persistence.*;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private double unitPrice;

    @ManyToMany(mappedBy = "categories")
    private List<ServicesSet> servicesSets = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Booking> bookings = new ArrayList<>();

}
