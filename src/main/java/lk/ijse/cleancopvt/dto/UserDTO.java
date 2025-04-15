package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Embedded.Address;
import lk.ijse.cleancopvt.Embedded.Name;
import lk.ijse.cleancopvt.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String profilePic;
    private String nicNumber;
    private Name name;
    private Address address;
    private Role role;
    private boolean active;
    private String primaryContact;
    private String secondaryContact;
    private String email;
    private String password;
}
