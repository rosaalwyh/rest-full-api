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


public class CreateContactRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    private String firstName;

    @Size(min = 2, max = 100)
    private String lastName;

    @Size(min = 2, max = 100)
    @Email
    private String email;

    @Size(min = 2, max = 100)
    private String phone;
}
