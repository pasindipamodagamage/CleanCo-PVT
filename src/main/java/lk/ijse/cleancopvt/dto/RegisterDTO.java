package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Embedded.Name;
import lk.ijse.cleancopvt.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO extends UserDTO {
    private Name name;
    private Role role;
    private boolean active;
    private String email;
    private String password;
}
