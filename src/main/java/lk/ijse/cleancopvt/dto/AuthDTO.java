package lk.ijse.cleancopvt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthDTO extends UserDTO {
//    private String email;
    private UserDTO user;
    private String token;
}