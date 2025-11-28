package es.daw.foodexpressmvc.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    @Size(min = 9, max = 20, message = "Phone must be between 9 and 20 digits")
    private String phone;

}
