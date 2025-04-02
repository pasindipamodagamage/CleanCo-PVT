package lk.ijse.cleancopvt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

//    private String profilePic;

    private double unitPrice;

}
