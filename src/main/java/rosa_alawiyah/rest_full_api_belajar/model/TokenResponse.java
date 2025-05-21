package rosa_alawiyah.rest_full_api_belajar.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class TokenResponse {
    private String token;

    private Long expiredDate;
}
