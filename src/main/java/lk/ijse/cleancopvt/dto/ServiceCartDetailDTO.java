package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCartDetailDTO {
    private String categoryName;
    private String serviceName;
    private Duration duration;
    private double total;
}
