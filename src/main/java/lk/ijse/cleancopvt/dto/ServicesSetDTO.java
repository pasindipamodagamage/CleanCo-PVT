package lk.ijse.cleancopvt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicesSetDTO {
    private UUID id;
    private String serviceName;
    private String description;
    private double unitPrice;
    private List<UUID> categoryIdList;
    private List<String> categoryNames;
}
