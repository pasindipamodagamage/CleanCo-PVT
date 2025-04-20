package lk.ijse.cleancopvt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthDTO{
    private String email;
    private String token;
    private Boolean active;
    private UUID id;
}