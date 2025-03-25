package lk.ijse.cleanco.Entity;

import jakarta.persistence.*;
import lk.ijse.cleanco.Embedded.Address;
import lk.ijse.cleanco.Embedded.Name;
import lk.ijse.cleanco.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String profilePic;
    private String nicNumber;
    private Name name;
    private Address address;
    private Role role;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;
    private String primaryContact;
    private String secondaryContact;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

}
