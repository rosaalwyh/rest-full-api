package rosa_alawiyah.rest_full_api_belajar.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContactResponse {
    private  String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
