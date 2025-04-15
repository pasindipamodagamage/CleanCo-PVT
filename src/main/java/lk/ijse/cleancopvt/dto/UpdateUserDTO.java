package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Embedded.Address;
import lk.ijse.cleancopvt.Embedded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDTO {
    private String profilePic;
    private String email;
    private String nicNumber;
    private Name name;
    private Address address;
    private String primaryContact;
    private String secondaryContact;
    private String password;
    private String currentPassword;
}
