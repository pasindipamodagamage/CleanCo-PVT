package lk.ijse.cleancopvt.dto;

import lk.ijse.cleancopvt.Enum.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicesSetDTO {
    private int id;

    private String serviceName;

    private ServiceType serviceType;

    private String description;

//    private String duration;
//
//    private double price;

}
