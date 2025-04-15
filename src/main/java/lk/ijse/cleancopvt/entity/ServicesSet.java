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
public class ServicesSet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String serviceName;
    private String description;
    private double unitPrice;

    @ManyToMany
    @JoinTable(
            name = "service_category",
            joinColumns = @JoinColumn(name = "service_set_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

}
