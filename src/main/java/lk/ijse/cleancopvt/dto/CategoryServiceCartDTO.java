package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryServiceCartDTO {
    private UUID id;
    private String categoryName;
    private Duration duration;
    private List<String> serviceNames;
    private double total;

}
