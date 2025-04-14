package lk.ijse.cleancopvt.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUtil {
    private int code;
    private String message;
    private Object data;
}
